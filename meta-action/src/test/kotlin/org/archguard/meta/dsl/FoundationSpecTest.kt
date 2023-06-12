package org.archguard.meta.dsl

import org.archguard.meta.base.FakeRuleVerifier
import org.archguard.meta.base.RuleResult
import org.archguard.meta.dsl.matcher.shouldBe
import org.archguard.meta.dsl.matcher.shouldNotBe
import org.archguard.meta.model.FoundationElement
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class FoundationSpecTest {
    @Test
    fun spec_checking() {
        val governance = foundation {
            project_name {
                pattern("^([a-z0-9-]+)-([a-z0-9-]+)-([a-z0-9-]+)(-common)?\$")
                example("system1-servicecenter1-microservice1")
            }

            layered {
                layer("application") {
                    pattern(".*\\.model") { filename shouldBe endWiths("DTO", "Request", "Response") }
                }

                layer("domain") {
                    pattern(".*\\.model") { filename shouldBe endWiths("Entity") }
                    pattern(".*\\.model") { filename shouldNotBe endWiths("Entity") }
                }

                dependency {
                    "domain" dependedOn "infrastructure"
                    "application" dependedOn "domain"
                }
            }

            naming {
                class_level {
                    style("CamelCase")
                    pattern(".*") { name shouldNotBe contains("$") }
                }
                function_level {
                    style("CamelCase")
                    pattern(".*") { name shouldNotBe contains("$") }
                }
            }
        }

        val foundation = FoundationElement("error-project_name", listOf())
        governance.context(FakeRuleVerifier())
        val result: Map<String, RuleResult> = governance.exec(foundation)

        assertEquals(result.size, 1)
        val ruleResult = result["ProjectName"]!!
        assertEquals(ruleResult.ruleName, "ProjectName")
    }
}