package org.archguard.spec.lang.foundation.rule

import org.archguard.spec.element.FoundationElement
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class ProjectNameRuleTest {
    @Test
    fun should_return_true_when_project_name_correct() {
        val projectNameRule = project_name {
            pattern("^([a-z0-9-]+)-([a-z0-9-]+)-([a-z0-9-]+)(-common)?\$")
            example("system1-servicecenter1-microservice1")
        }

        val exec = projectNameRule.exec(FoundationElement("ms-sc-auth", listOf()))
        assertEquals(1, exec.size)
        assertEquals(true, exec[0].success)
    }

    @Test
    fun should_return_false_when_project_name_incorrect() {
        val projectNameRule = project_name {
            pattern("^([a-z0-9-]+)-([a-z0-9-]+)-([a-z0-9-]+)(-common)?\$")
            example("system1-servicecenter1-microservice1")
        }

        val exec = projectNameRule.exec(FoundationElement("ms-sc", listOf()))
        assertEquals(1, exec.size)
        assertEquals(false, exec[0].success)
    }
}
