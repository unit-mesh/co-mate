package org.archguard.architecture.style

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class NamingStyleTest {
    @Test
    fun should_convert_to_snake_case() {
        val name = NamingStyle.toSnakeCase("HelloWorld")
        assertEquals("hello_world", name)

        // three words
        val name2 = NamingStyle.toSnakeCase("HelloWorldAgain")
        assertEquals("hello_world_again", name2)
    }
}