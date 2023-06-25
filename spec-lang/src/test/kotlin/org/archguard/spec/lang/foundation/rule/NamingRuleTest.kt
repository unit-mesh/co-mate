package org.archguard.spec.lang.foundation.rule

import org.archguard.spec.lang.matcher.CompareType
import org.archguard.spec.lang.matcher.shouldBe
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class NamingRuleTest {
    @Test
    fun should_success_pass_by_rule() {
        val namingRule = naming {
            name shouldBe endWiths("t")
        }

        assertEquals("", namingRule.string)
        assertEquals(CompareType.END_WITHS, namingRule.delayCompare!!.compareType)

        val exec = namingRule.exec("test")
        assertEquals(1, exec.size)
        assertEquals(true, exec[0].success)
    }

    @Test
    fun should_failed_when_not_match() {
        val namingRule = naming {
            name shouldBe endWiths("t")
        }

        val exec = namingRule.exec("test1")
        assertEquals(1, exec.size)
        assertEquals(false, exec[0].success)
    }
}