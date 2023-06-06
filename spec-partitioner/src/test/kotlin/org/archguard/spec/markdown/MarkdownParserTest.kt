package org.archguard.spec.markdown

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class MarkdownParserTest {
    @Test
    fun parse_by_title() {
        val markdown = """
HTTP response status codes
==========================

The server should always return the right HTTP status code to the client. (Recommended)
        """.trimIndent()

        val parser = MarkdownParser()
        val result = parser.parseAndOrganizeByTitle(markdown)
//        assertEquals(1, result.size)
//
//        val contentBlocking = result[0]
//        assertEquals(contentBlocking.subTitle, "HTTP response status codes")
//        assertEquals(
//            contentBlocking.content,
//            "The server should always return the right HTTP status code to the client. (Recommended)"
//
//        )
    }
}