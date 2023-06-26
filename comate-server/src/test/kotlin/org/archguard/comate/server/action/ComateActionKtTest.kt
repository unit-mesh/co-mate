package org.archguard.comate.server.action

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class ComateActionKtTest {
    val expect = "https://github.com/archguard/co-mate"

    @Test
    fun should_correct_parse_url() {
        assertEquals(expect, parseUrlFromRequest(expect))
        assertEquals(expect, parseUrlFromRequest("'https://github.com/archguard/co-mate'"))
    }
}