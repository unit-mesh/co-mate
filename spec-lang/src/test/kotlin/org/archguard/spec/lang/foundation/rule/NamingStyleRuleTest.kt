package org.archguard.spec.lang.foundation.rule

import chapi.domain.core.CodeDataStruct
import chapi.domain.core.CodeFunction
import org.archguard.spec.element.FoundationElement
import org.archguard.spec.lang.foundation.declaration.NamingTarget
import org.archguard.spec.lang.matcher.shouldNotBe
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class NamingStyleRuleTest {
    @Test
    fun should_return_true_when_match_naming_style() {
        val namingStyleRule = naming_style_t({
            style("CamelCase")
            pattern(".*") { name shouldNotBe contains("${'$'}") }
        }, NamingTarget.Class)

        val results = namingStyleRule.exec(
            FoundationElement(
                "test", listOf(
                    CodeDataStruct(
                        NodeName = "test-hello",
                    )
                )
            )
        ).filter { !it.success }

        assertEquals(1, results.size)
        assertEquals(false, results[0].success)
        assertEquals("Naming for Class", results[0].name)
    }

    @Test
    fun should_return_true_when_match_naming_style_for_function() {
        val namingStyleRule = naming_style_t({
            style("CamelCase")
            pattern(".*") { name shouldNotBe contains("${'$'}") }
        }, NamingTarget.Function)

        val functions = listOf(CodeFunction(Name = "test-hello"))
        val dataStruct = CodeDataStruct(
            NodeName = "NormalClass",
            Functions = functions
        )
        val results = namingStyleRule.exec(
            FoundationElement("test", listOf(dataStruct))
        ).filter { !it.success }

        assertEquals(1, results.size)
        assertEquals(false, results[0].success)
        assertEquals("Naming for Function", results[0].name)
    }

    @Test
    fun should_return_correct_to_string_item() {
        val namingStyleRule = naming_style_t({
            style("CamelCase")
            pattern(".*") { name shouldNotBe contains("${'$'}") }
        }, NamingTarget.Class)

        assertEquals(
            namingStyleRule.toString(), """
            class_level {
                style("CamelCase")
                pattern(".*") { name shouldNotBe contains("${'$'}") }
            }
            """.trimIndent()
        )
    }
}