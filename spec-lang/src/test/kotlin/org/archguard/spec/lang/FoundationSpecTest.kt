package org.archguard.spec.lang

import chapi.domain.core.CodeDataStruct
import chapi.domain.core.CodeFunction
import chapi.domain.core.CodeImport
import org.archguard.spec.base.verifier.FakeRuleVerifier
import org.archguard.spec.base.RuleResult
import org.archguard.spec.lang.matcher.shouldBe
import org.archguard.spec.lang.matcher.shouldNotBe
import org.archguard.spec.element.FoundationElement
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class FoundationSpecTest {
    private val governance = foundation {
        project_name {
            pattern("^([a-z0-9-]+)-([a-z0-9-]+)-([a-z0-9-]+)(-common)?\$")
            example("system1-servicecenter1-microservice1")
        }

        layered {
            layer("application") {
                pattern(".*\\.application") { name shouldBe endWiths("DTO", "Request", "Response") }
            }
            layer("domain") {
                pattern(".*\\.domain") { name shouldBe endWiths("Entity") }
            }
            layer("infrastructure") {
                pattern(".*\\.infrastructure") { name shouldBe endWiths("Repository", "Mapper") }
            }
            layer("interface") {
                pattern(".*\\.interface") { name shouldBe endWiths("Controller", "Service") }
            }

            dependency {
                "application" dependedOn "domain"
                "application" dependedOn "interface"
                "domain" dependedOn "infrastructure"
                "interface" dependedOn "domain"
            }
        }

        naming {
            class_level {
                style("CamelCase")
                pattern(".*") { name shouldNotBe contains("$") }
            }
            function_level {
                style("CamelCase")
                pattern(".*") { name shouldNotBe contains("$") }
            }
        }
    }

    @Test
    fun should_equal_when_had_correct_project_name() {
        val foundation = FoundationElement("error-project_name", listOf())
        governance.setVerifier(FakeRuleVerifier())
        val results: List<RuleResult> = governance.exec(foundation)

        val projectNameResult = results.filter { it.name == "ProjectName" }
        assertEquals(projectNameResult[0].success, false)
    }

    @Test
    fun should_return_false_when_class_error() {
        val ds = CodeDataStruct("error_Class_Name")
        val foundation = FoundationElement("system1-servicecenter1-microservice1", listOf(ds))

        val results: List<RuleResult> = governance.exec(foundation)
        val projectNameResult = results.filter { it.name == "ProjectName" }

        assertEquals(projectNameResult[0].success, true)

        val namingResult = results.filter { it.name == "Naming for Class" && !it.success }
        assertEquals(namingResult.size, 1)
    }

    @Test
    fun should_return_true_when_class_name_correct() {
        val ds = CodeDataStruct("ClassName")
        val foundation = FoundationElement("system1-servicecenter1-microservice1", listOf(ds))

        val results: List<RuleResult> = governance.exec(foundation)

        val projectNameResult = results.filter { it.name == "ProjectName" }
        assertEquals(projectNameResult[0].success, true)

        val namingResult = results.filter { it.name == "Naming" }
        assertEquals(namingResult[0].success, true)
    }

    @Test
    fun should_return_false_when_function_name_contains_dollar() {
        val ds = CodeDataStruct("ClassName", Functions = listOf(CodeFunction("function_name$1")))
        val foundation = FoundationElement("system1-servicecenter1-microservice1", listOf(ds))

        val result: List<RuleResult> = governance.exec(foundation)

        val errorResult = result.filter { !it.success }
        assertEquals(errorResult.size, 1)
        assertEquals(errorResult[0].name, "Naming for Function")
    }

    @Test
    fun should_return_false_when_layered_error() {
        val ds = CodeDataStruct("NotMatchClassName", Package = "com.example.application")
        val foundation = FoundationElement("system1-servicecenter1-microservice1", listOf(ds))

        val result: List<RuleResult> = governance.exec(foundation)

        val errorResult = result.filter { !it.success }
        assertEquals(errorResult.size, 1)
        assertEquals(errorResult[0].name, "Layered for application")
    }

    @Test
    fun should_return_true_when_application_name_correctly() {
        val ds = CodeDataStruct("BlogRequest", Package = "com.example.application")
        val foundation = FoundationElement("system1-servicecenter1-microservice1", listOf(ds))

        val result: List<RuleResult> = governance.exec(foundation)

        val errorResult = result.filter { !it.success }
        assertEquals(errorResult.size, 0)
    }

    @Test
    fun should_return_false_when_depends_on_incorrect() {
        val domainDs = CodeDataStruct(
            "MetaAction",
            Package = "org.archguard.domain",
            Imports = listOf(CodeImport("org.archguard.application.DemoService"))
        )
        val applicationDs = CodeDataStruct("DemoService", Package = "org.archguard.application")
        val foundation = FoundationElement("system1-servicecenter1-microservice1", listOf(domainDs, applicationDs))

        val result: List<RuleResult> = governance.exec(foundation)

        val errorResult = result.filter { it.name == "DependencyRule" && !it.success }
        assertEquals(errorResult.size, 1)
    }

    @Test
    fun should_no_error_when_depends_on_correct() {
        val imports = listOf(CodeImport("org.archguard.domain.MetaAction"))
        val domainDs = CodeDataStruct("DemoApp", Package = "org.archguard.application", Imports = imports)
        val applicationDs = CodeDataStruct("MetaAction", Package = "org.archguard.domain")
        val foundation = FoundationElement("system1-servicecenter1-microservice1", listOf(domainDs, applicationDs))

        val result: List<RuleResult> = governance.exec(foundation)

        val errorResult = result.filter { it.name == "DependencyRule" && !it.success }
        assertEquals(errorResult.size, 0)
    }
}