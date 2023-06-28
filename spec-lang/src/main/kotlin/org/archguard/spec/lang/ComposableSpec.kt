package org.archguard.spec.lang

import kotlinx.serialization.Serializable
import org.archguard.spec.base.RuleResult
import org.archguard.spec.lang.base.Spec

@Serializable
data class Scenario(val name: String, val description: String)

class ComposableSpec : Spec<Scenario> {
    override fun exec(element: Scenario): List<RuleResult> = listOf()

    override fun default(): Spec<Scenario> {
        return defaultSpec()
    }

    companion object {
        fun defaultSpec(): ComposableSpec {
            return composable {

            }
        }
    }
}

fun composable(function: ComposableSpec.() -> Unit): ComposableSpec {
    val spec = ComposableSpec()
    spec.function()
    return spec
}