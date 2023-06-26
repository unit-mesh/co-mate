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

    @Test
    fun should_verify_naming_style() {
        val name = "HelloWorld"
        assertEquals(true, NamingStyle.UpperCamelCase.isValid(name))
        assertEquals(true, NamingStyle.CamelCase.isValid(name))
        assertEquals(false, NamingStyle.SnakeCase.isValid(name))
        assertEquals(false, NamingStyle.KebabCase.isValid(name))

        val name2 = "helloWorld"
        assertEquals(false, NamingStyle.UpperCamelCase.isValid(name2))
        assertEquals(true, NamingStyle.CamelCase.isValid(name2))
        assertEquals(false, NamingStyle.SnakeCase.isValid(name2))
        assertEquals(false, NamingStyle.KebabCase.isValid(name2))

        val name3 = "hello_world"
        assertEquals(false, NamingStyle.UpperCamelCase.isValid(name3))
        assertEquals(false, NamingStyle.CamelCase.isValid(name3))
        assertEquals(true, NamingStyle.SnakeCase.isValid(name3))
        assertEquals(false, NamingStyle.KebabCase.isValid(name3))

        val name4 = "hello-world"
        assertEquals(false, NamingStyle.UpperCamelCase.isValid(name4))
        assertEquals(false, NamingStyle.CamelCase.isValid(name4))
        assertEquals(false, NamingStyle.SnakeCase.isValid(name4))
        assertEquals(true, NamingStyle.KebabCase.isValid(name4))
    }
}