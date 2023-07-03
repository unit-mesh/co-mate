package org.archguard.spec.lang.caseflow.declaration

class StoryDeclaration(val name: String) {
    val sceneDeclarations: MutableList<SceneDeclaration> = mutableListOf()

    fun scene(scenario: String, function: SceneDeclaration.() -> Unit): SceneDeclaration {
        val sceneDeclaration = SceneDeclaration(scenario)
        sceneDeclaration.function()
        sceneDeclarations.add(sceneDeclaration)
        return sceneDeclaration
    }
}