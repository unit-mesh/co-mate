package org.archguard.comate.dynamic

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class DynamicContextTest {

    @Test
    fun should_return_all_explains() {
        DynamicContext.explains().forEach {
            println(it)
        }
    }
}
