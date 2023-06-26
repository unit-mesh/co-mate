package org.archguard.spec.lang.foundation.rule

import chapi.domain.core.CodeDataStruct
import org.archguard.spec.element.FoundationElement
import org.archguard.spec.lang.foundation.declaration.NamingTarget
import org.archguard.spec.lang.matcher.shouldNotBe
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class NamingStyleRuleTest {
    @Test
    fun should_return_true_when_match_naming_style() {
        val namingStyleRule = test_naming_style({
            style("CamelCase")
            pattern(".*") { name shouldNotBe contains("${'$'}") }
        }, NamingTarget.Class)

        val results = namingStyleRule.exec(FoundationElement("test", listOf(CodeDataStruct(
            NodeName = "test-hello",
        ))))

        assertEquals(2, results.size)
        assertEquals(true, results[0].success)
        assertEquals(true, results[1].success)
    }
}