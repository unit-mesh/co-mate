package org.archguard.spec.lang.foundation.declaration

import chapi.domain.core.CodeDataStruct
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import org.archguard.spec.element.FoundationElement
import org.archguard.spec.lang.foundation.rule.DependencyRule
import org.archguard.spec.lang.matcher.shouldBe
import org.archguard.spec.lang.matcher.shouldNotBe
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class LayeredDeclarationTest {
    @Test
    fun should_correct_setup_for_deps() {
        val layeredDeclaration = layered_t {
            layer("interface") {
                pattern(".*\\.apis") { name shouldBe endsWith("Controller") }
            }
            layer("application") {
                pattern(".*\\.application") {
                    name shouldBe endsWith("DTO", "Request", "Response", "Factory", "Service")
                }
            }
            layer("domain") {
                pattern(".*\\.domain(?:\\.[a-zA-Z]+)?") { name shouldNotBe endsWith("Request", "Response") }
            }
            layer("infrastructure") {
                pattern(".*\\.infrastructure") { name shouldBe endsWith("Repository", "Mapper") }
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

    @Test
    fun should_find_error_layered_dep() {
        val layeredDeclaration = layered_t {
            layer("interface") {
                pattern(".*\\.apis") { name shouldBe endsWith("Controller") }
            }
            layer("application") {
                pattern(".*\\.application") {
                    name shouldBe endsWith("DTO", "Request", "Response", "Factory", "Service")
                }
            }
            layer("domain") {
                pattern(".*\\.domain(?:\\.[a-zA-Z]+)?") { name shouldNotBe endsWith("Request", "Response") }
            }
            layer("infrastructure") {
                pattern(".*\\.infrastructure") { name shouldBe endsWith("Repository", "Mapper") }
            }

            dependency {
                "application" dependedOn "interface"
            }
        }

        val dsString = this.javaClass.classLoader.getResource("spec/ddd-mono-repo-demo.json")!!.readText()
        val ds: List<CodeDataStruct> = Json.decodeFromString(dsString)
        val foundationElement = FoundationElement("ddd-mono-repo-demo", ds)

        val rules = layeredDeclaration.rules(foundationElement)

        foundationElement.layeredDefines = rules.filterIsInstance<LayeredDefine>()
        assertEquals(5, rules.size)

        val dependencyRule = rules.filterIsInstance<DependencyRule>().first()
        val ruleResults = dependencyRule.exec(foundationElement)

        assertEquals(2, ruleResults.size)
    }

    @Test
    fun should_get_origin_text_in_string() {
        val declaration = layered_t {
            layer("interface") {
                pattern(".*\\.apis") { name shouldBe endsWith("Controller") }
            }
            layer("application") {
                pattern(".*\\.application") {
                    name shouldBe endsWith("DTO", "Request", "Response", "Factory", "Service")
                }
            }
            layer("domain") {
                pattern(".*\\.domain(?:\\.[a-zA-Z]+)?") { name shouldNotBe endsWith("Request", "Response") }
            }
            layer("infrastructure") {
                pattern(".*\\.infrastructure") { name shouldBe endsWith("Repository", "Mapper") }
            }

            dependency {
                "application" dependedOn "interface"
            }
        }

        assertEquals(declaration.toString(), """layered("interface") {
    pattern(".*\.apis") { name shouldBe endsWith("Controller") }
}
layered("application") {
    pattern(".*\.application") { name shouldBe endsWith("DTO", "Request", "Response", "Factory", "Service") }
}
layered("domain") {
    pattern(".*\.domain(?:\.[a-zA-Z]+)?") { name shouldNotBe endsWith("Request", "Response") }
}
layered("infrastructure") {
    pattern(".*\.infrastructure") { name shouldBe endsWith("Repository", "Mapper") }
}
dependency {
    "application" dependedOn "interface"
}""")
    }
}