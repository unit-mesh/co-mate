package org.archguard.spec.lang

import kotlinx.serialization.Serializable
import org.archguard.spec.lang.base.Spec

@Serializable
data class Class(
    val packageName: String,
    val name: String,
    val properties: MutableList<Property> = mutableListOf(),
    val methods: MutableList<Method> = mutableListOf(),
)

@Serializable
data class Property(val name: String, val type: String)

@Serializable
data class Method(val name: String, val returnType: String, val parameters: List<Parameter> = emptyList())

@Serializable
data class Parameter(val name: String, val type: String)

@Serializable
data class Behavior(val action: String, val description: String)

/**
 * Concept is a class abstraction for a concept, will be used to generate code.
 */
class ConceptDeclaration(private val className: String, private val packageName: String) {
    private val properties = mutableListOf<Property>()
    private val methods = mutableListOf<Method>()
    private val innerBehaviors = mutableListOf<Behavior>()

    /**
     * Define a list of [Behavior], for example:
     *
     * ```kotlin
     * behaviors = listOf("View Menu", "Place Order", "Pay", "View Order Status", "View Order History")
     * ```
     */
    var behaviors: List<String> = emptyList()

    fun prop(name: String, type: String) {
        properties.add(Property(name, type))
    }

    fun method(name: String, returnType: String, block: MethodBuilder.() -> Unit) {
        val methodBuilder = MethodBuilder(name, returnType)
        methodBuilder.block()
        methods.add(methodBuilder.build())
    }

    fun build(): Class {
        return Class(packageName, className, properties, methods)
    }

    fun prop(name: Pair<String, String>) {
        properties.add(Property(name.first, name.second))
    }

    /**
     * behavior is a synonym of usecase, same to [ConceptDeclaration.usecase]
     * can define with description or empty
     * ```kotlin
     * behavior("Place Order", "Place an order for a coffee")
     * behavior("Place Order")
     * ```
     */
    fun behavior(action: String, description: String = "") {
        innerBehaviors.add(Behavior(action, description))
    }

    /**
     * same to [ConceptDeclaration.behavior]
     */
    fun usecase(action: String, description: String) {
        innerBehaviors.add(Behavior(action, description))
    }

    inner class MethodBuilder(private val name: String, private val returnType: String) {
        private val parameters = mutableListOf<Parameter>()

        fun parameter(name: String, type: String) {
            parameters.add(Parameter(name, type))
        }

        fun build(): Method {
            return Method(name, returnType, parameters)
        }
    }
}

class ConceptSpec : Spec<String> {
    override fun default(): Spec<String> {
        return defaultSpec()
    }

    override fun toString(): String {
        return "ConceptSpec()"
    }

    companion object {
        fun defaultSpec(): ConceptSpec {
            return concepts {
                concept("Customer") {
                    behavior("Place Order", "Place an order for a coffee")
                    behaviors = listOf("View Menu", "Place Order", "Pay", "View Order Status", "View Order History")
                }
                concept("Barista") {
                    behavior("Make Coffee")
                }

                concept("Person", "com.biz.domain") {
                    prop("name" to "String")
                    prop("age", "Int")

                    method("speak", "Unit") {
                        parameter("message", "String")
                    }
                }
            }
        }
    }

    /**
     * Concept
     */
    fun concept(className: String, packageName: String = "", function: ConceptDeclaration.() -> Unit): ConceptDeclaration {
        val conceptDeclaration = ConceptDeclaration(className, packageName)
        conceptDeclaration.function()
        return conceptDeclaration
    }
}

/**
 * Concept DSL is a way to describe the concept(object) and their connections of a software system.
 */
fun concepts(function: ConceptSpec.() -> Unit): ConceptSpec {
    val conceptSpec = ConceptSpec()
    conceptSpec.function()
    return conceptSpec
}
