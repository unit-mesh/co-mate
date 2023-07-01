package org.archguard.spec.lang

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class CaseFlowSpecTest {
    @Test
    fun default() {
        val spec = CaseFlowSpec.defaultSpec()
        assertEquals(CaseFlowSpec.defaultSpec().toString(), spec.toString())
    }

}