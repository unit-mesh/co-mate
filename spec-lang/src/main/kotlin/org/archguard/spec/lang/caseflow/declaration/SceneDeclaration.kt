package org.archguard.spec.lang.caseflow.declaration

import org.archguard.spec.lang.caseflow.model.Scene

class SceneDeclaration(val description: String) {
    val steps: MutableList<NamedStep> = mutableListOf()

    fun Given(description: String) {
        steps.add(NamedStep(description))
    }

    fun And(description: String) {
        steps.add(NamedStep(description))
    }

    fun When(description: String) {
        steps.add(NamedStep(description))
    }

    fun Then(description: String) {
        steps.add(NamedStep(description))
    }

    fun toModel(): Scene {
        return Scene(description, steps.map { it.description })
    }

    inner class NamedStep(val description: String)
}