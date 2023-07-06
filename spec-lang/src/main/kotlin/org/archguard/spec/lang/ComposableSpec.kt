package org.archguard.spec.lang

import kotlinx.serialization.Serializable
import org.archguard.spec.base.RuleResult
import org.archguard.spec.lang.base.Spec

@Serializable
data class Scenario(val name: String, val description: String)

@SpecDsl
class ComposableSpec : Spec<Scenario> {
    override fun exec(element: Scenario): List<RuleResult> = listOf()

    override fun default(): Spec<Scenario> {
        return defaultSpec()
    }

    companion object {
        fun defaultSpec(): ComposableSpec {
            return composable {
                channels {

                }
                integrations { }
                business { }
                records { }
                capabilities { }
            }
        }
    }

    fun channels(function: ChannelsDeclaration.() -> Unit): ChannelsDeclaration {
        val channelsDeclaration = ChannelsDeclaration()
        channelsDeclaration.function()
        return channelsDeclaration
    }

    inner class ChannelsDeclaration() {

    }

    fun integrations(function: () -> Unit) {

    }

    fun business(function: () -> Unit) {

    }

    fun records(function: () -> Unit) {

    }

    fun capabilities(function: () -> Unit) {

    }
}

/**
 * Composable DSL is an EA (enterprise architecture) specification language with a focus on composable architecture.
 * It is used to describe the architecture of a software system.
 */
fun composable(function: ComposableSpec.() -> Unit): ComposableSpec {
    val spec = ComposableSpec()
    spec.function()
    return spec
}