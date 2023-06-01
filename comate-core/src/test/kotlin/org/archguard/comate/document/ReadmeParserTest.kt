package org.archguard.comate.document

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Assertions.*
import kotlin.test.Test

class ReadmeParserTest {
    @Test
    fun test_introduction() {
        val parser = ReadmeParser(
            """
[![JetBrains Research](https://jb.gg/badges/research.svg)](https://confluence.jetbrains.com/display/ALL/JetBrains+on+GitHub)

# Reflekt

Reflekt is a compile-time reflection library that leverages the flows of the 
standard reflection approach and can find classes, objects (singleton classes) or functions 
by some conditions in compile-time.

Instead of relying on JVM reflection, Reflekt performs compile-time resolution of reflection queries
using Kotlin compiler analysis, providing a convenient reflection API without actually using
reflection.

        """.trimIndent()
        )

        val introduction = parser.introduction()

        introduction.title shouldBe "Reflekt"
        introduction.content shouldBe """Reflekt is a compile-time reflection library that leverages the flows of the
standard reflection approach and can find classes, objects (singleton classes) or functions
by some conditions in compile-time."""
    }
}