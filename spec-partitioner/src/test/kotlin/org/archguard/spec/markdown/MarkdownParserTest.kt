package org.archguard.spec.markdown

import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test

class MarkdownParserTest {
    @Test
    @Disabled
    fun parse_by_title() {
        val markdown = """
HTTP response status codes
==========================

The server should always return the right HTTP status code to the client. (Recommended)
        """.trimIndent()

        val parser = MarkdownParser()
        val result = MarkdownParser.parseAndOrganizeByTitle(markdown)
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