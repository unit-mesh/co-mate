package org.archguard.meta.dsl

import chapi.domain.core.CodeDataStruct
import org.archguard.meta.base.FakeRuleVerifier
import org.archguard.meta.base.RuleResult
import org.archguard.meta.dsl.matcher.shouldBe
import org.archguard.meta.dsl.matcher.shouldNotBe
import org.archguard.meta.model.FoundationElement
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class FoundationSpecTest {
    private val governance = foundation {
        project_name {
            pattern("^([a-z0-9-]+)-([a-z0-9-]+)-([a-z0-9-]+)(-common)?\$")
            example("system1-servicecenter1-microservice1")
        }

        layered {
            layer("application") {
                pattern(".*\\.application") { string shouldBe endWiths("DTO", "Request", "Response") }
            }

            layer("domain") {
                pattern(".*\\.domain") { string shouldBe endWiths("Entity") }
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

    @Test
    fun should_equal_when_had_correct_project_name() {
        val foundation = FoundationElement("error-project_name", listOf())
        governance.context(FakeRuleVerifier())
        val result: Map<String, RuleResult> = governance.exec(foundation)

        assertEquals(result["ProjectName"]!!.result, false)
        val ruleResult = result["ProjectName"]!!
        assertEquals(ruleResult.ruleName, "ProjectName")
    }

    @Test
    fun should_verify_basic_class_name() {
        val ds = CodeDataStruct("error_Class_Name")
        val foundation = FoundationElement("system1-servicecenter1-microservice1", listOf(ds))
        governance.context(FakeRuleVerifier())

        val result: Map<String, RuleResult> = governance.exec(foundation)

        assertEquals(result["ProjectName"]!!.result, true)
        assertEquals(result["NamingItem for Class"]!!.result, false)
    }
}