package org.archguard.spec.lang.caseflow.declaration

import org.archguard.spec.lang.caseflow.Story

class StoryDeclaration(val name: String) {
    private val sceneDeclarations: MutableList<SceneDeclaration> = mutableListOf()

    fun scene(scenario: String, function: SceneDeclaration.() -> Unit): SceneDeclaration {
        val sceneDeclaration = SceneDeclaration(scenario)
        sceneDeclaration.function()
        sceneDeclarations.add(sceneDeclaration)
        return sceneDeclaration
    }

    fun toModel(): Story {
        return Story(name, sceneDeclarations.map { it.toModel() })
    }

    override fun toString(): String {
        return """
            |story("$name") {
            |${sceneDeclarations.joinToString("\n") { "    ${it.toString().replace("\n", "\n    ")}" }}
            |    }
        """.trimMargin()
    }
}