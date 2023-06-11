package org.archguard.spec.markdown

import io.kotest.matchers.shouldBe
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

    @Test
    fun should_pass_table_body() {
        val markdown = """
| HTTP status code | Description |
| --- | --- |
| 200 | OK |
| 201 | Created |
"""
        val result = MarkdownParser.tableToHashMap(markdown)
        result.size shouldBe 2

        result["HTTP status code"] shouldBe listOf("200", "201")
        result["Description"] shouldBe listOf("OK", "Created")
    }

    @Test
    fun should_parse_code_block() {
        val markdown = """
```java
public class Test {}
```
"""
        val result = MarkdownParser.parseMarkdownCodeBlock(markdown, "java")
        result.size shouldBe 1
    }

    @Test
    fun should_return_null_when_no_match_lang_code_block() {
        val markdown = """
```java
public class Test {}
```
"""
        val result = MarkdownParser.parseMarkdownCodeBlock(markdown, "kotlin")
        result.size shouldBe 0
    }
}