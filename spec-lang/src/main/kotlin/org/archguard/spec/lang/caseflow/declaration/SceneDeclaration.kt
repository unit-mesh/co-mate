package org.archguard.spec.lang.caseflow.declaration

import org.archguard.spec.lang.caseflow.model.Scene

enum class StepType(val value: String) {
    GIVEN("Given"),
    WHEN("When"),
    THEN("Then"),
    AND("And");
}

class SceneDeclaration(val description: String) {
    val steps: MutableList<NamedStep> = mutableListOf()

    fun Given(description: String) {
        steps.add(NamedStep(StepType.GIVEN, description))
    }

    fun And(description: String) {
        steps.add(NamedStep(StepType.AND, description))
    }

    fun When(description: String) {
        steps.add(NamedStep(StepType.WHEN, description))
    }

    fun Then(description: String) {
        steps.add(NamedStep(StepType.THEN, description))
    }

    fun toModel(): Scene {
        return Scene(description, steps.map { it.description })
    }

    override fun toString(): String {
        return """
            |    scene("$description") {
            |${steps.joinToString("\n") { "        ${it.type.value}(\"${it.description}\")".replace("\n", "\n") }}
            |    }
        """.trimMargin()
    }

    inner class NamedStep(val type: StepType, val description: String)
}