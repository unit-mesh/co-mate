package org.archguard.spec.lang.foundation.rule

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class DependencyRuleTest {
    @Test
    fun should_return_correct_to_string_for_dependency() {
        val decl = dependency_t {
            "interface" dependedOn "application"
        }

        assertEquals(decl.toString(), "\"interface\" dependedOn \"application\"")
    }

    @Test
    fun should_return_correct_to_string_for_dependency_with_multiple_deps() {
        val decl = dependency_t {
            "interface" dependedOn "application"
            "interface" dependedOn "domain"
        }

        assertEquals(
            decl.toString(),
            """"interface" dependedOn "application"
"interface" dependedOn "domain""""
        )
    }
}
