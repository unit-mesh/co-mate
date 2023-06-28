package org.archguard.spec.lang

import org.archguard.spec.lang.base.Spec


class Step(val description: String)
class ScenarioDeclaration(val name: String) {
    val steps: MutableList<Step> = mutableListOf()

    fun Given(description: String) {
        steps.add(Step(description))
    }

    fun And(description: String) {
        steps.add(Step(description))
    }

    fun When(description: String) {
        steps.add(Step(description))
    }

    fun Then(description: String) {
        steps.add(Step(description))
    }
}

class FeatureDeclaration(name: String, tag: String) {
    fun Scenario(scene: String, function: ScenarioDeclaration.() -> Unit): ScenarioDeclaration {
        val scenarioDeclaration = ScenarioDeclaration(scene)
        scenarioDeclaration.function()
        return scenarioDeclaration
    }
}

class FeatureSpec(val name: String) : Spec<String> {
    override fun default(): Spec<String> {
        return defaultSpec()
    }

    fun Feature(name: String, tag: String = "", function: FeatureDeclaration.() -> Unit): FeatureDeclaration {
        val featureDeclaration = FeatureDeclaration(name, tag)
        featureDeclaration.function()
        return featureDeclaration
    }

    companion object {
        @JvmStatic
        fun defaultSpec(): FeatureSpec = FeatureSpec("")
    }
}

fun FeatureSuite(name: String, init: FeatureSpec.() -> Unit): FeatureSpec {
    val spec = FeatureSpec(name)
    spec.init()
    return spec
}
