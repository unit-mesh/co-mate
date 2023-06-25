package org.archguard.spec.lang.foundation.rule

import chapi.domain.core.CodeDataStruct
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import org.archguard.spec.element.FoundationElement
import org.archguard.spec.lang.foundation.declaration.LayeredDefine
import org.archguard.spec.lang.foundation.declaration.layered
import org.archguard.spec.lang.matcher.shouldBe
import org.archguard.spec.lang.matcher.shouldNotBe
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class DependencyRuleTest {
    @Test
    fun should_correct_setup_for_deps() {
        val layeredDeclaration = layered {
            layer("interface") {
                pattern(".*\\.apis") { name shouldBe endWiths("Controller") }
            }
            layer("application") {
                pattern(".*\\.application") {
                    name shouldBe endWiths("DTO", "Request", "Response", "Factory", "Service")
                }
            }
            layer("domain") {
                pattern(".*\\.domain(?:\\.[a-zA-Z]+)?") { name shouldNotBe endWiths("Request", "Response") }
            }
            layer("infrastructure") {
                pattern(".*\\.infrastructure") { name shouldBe endWiths("Repository", "Mapper") }
            }

            dependency {
                "interface" dependedOn "domain"
                "interface" dependedOn "application"
                "interface" dependedOn "infrastructure"
                "application" dependedOn "domain"
                "application" dependedOn "infrastructure"
                "domain" dependedOn "infrastructure"
            }
        }

        // load the ds from the test resources
        val dsString = this.javaClass.classLoader.getResource("spec/ddd-mono-repo-demo.json")!!.readText()
        val ds: List<CodeDataStruct> = Json.decodeFromString(dsString)
        val foundationElement = FoundationElement("ddd-mono-repo-demo", ds)

        val rules = layeredDeclaration.rules(foundationElement)

        foundationElement.layeredDefines = rules.filterIsInstance<LayeredDefine>()
        assertEquals(5, rules.size)

        val dependencyRule = rules.filterIsInstance<DependencyRule>().first()
        val ruleResults = dependencyRule.exec(foundationElement)

        assertEquals(0, ruleResults.size)
    }
}
