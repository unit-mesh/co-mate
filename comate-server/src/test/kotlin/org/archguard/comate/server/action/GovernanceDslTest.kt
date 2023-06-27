package org.archguard.comate.server.action

import org.archguard.spec.lang.FoundationSpec
import org.archguard.spec.runtime.KotlinInterpreter
import org.archguard.spec.runtime.interpreter.api.InterpreterRequest
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class GovernanceDslTest {
    private val repl = KotlinInterpreter()

    @Test
    fun should_success_create_dsl() {
        val mvcDsl = """foundation {
    project_name {
        pattern("^([a-z0-9-]+)-([a-z0-9-]+)(-common)?\${'$'}")
        example("system1-webapp1")
    }

    layered {
        layer("controller") {
            pattern(".*\\.controller") { name shouldBe endsWith("Controller") }
        }
        layer("service") {
            pattern(".*\\.service") {
                name shouldBe endsWith("DTO", "Request", "Response", "Factory", "Service")
            }
        }
        layer("repository") {
            pattern(".*\\.repository") { name shouldBe endsWith("Entity", "Repository", "Mapper") }
        }

        dependency {
            "controller" dependedOn "service"
            "controller" dependedOn "repository"
            "service" dependedOn "repository"
        }
    }

    naming {
        class_level {
            style("CamelCase")
            pattern(".*") { name shouldNotBe contains("${'$'}") }
        }
        function_level {
            style("CamelCase")
            pattern(".*") { name shouldNotBe contains("${'$'}") }
        }
    }
}"""
        val mvcFoundation = """
import org.archguard.spec.lang.*
import org.archguard.spec.lang.base.*
import org.archguard.spec.lang.domain.*
import org.archguard.spec.lang.foundation.*
import org.archguard.spec.lang.matcher.*
import org.archguard.spec.lang.restapi.*

$mvcDsl
"""


        val mvcDslSpec = repl.evalCast<FoundationSpec>(InterpreterRequest(code = mvcFoundation))

        assertNotNull(mvcDslSpec)
        assertEquals(FoundationSpec::class.java.canonicalName, mvcDslSpec.javaClass.canonicalName)
    }
}