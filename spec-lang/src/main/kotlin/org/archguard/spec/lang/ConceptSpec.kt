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
data class Behavior(val action: String, val description: String = "")

/**
 * Concept is a class abstraction for a concept, will be used to generate code.
 */
class ConceptDeclaration(private val className: String, private val packageName: String = "") {
    @Deprecated("use behavior instead")
    private val properties = mutableListOf<Property>()

    @Deprecated("use behavior instead")
    private val methods = mutableListOf<Method>()
    val formatBehaviors = mutableListOf<Behavior>()

    /**
     * Define a list of [Behavior], for example:
     *
     * ```kotlin
     * behaviors = listOf("View Menu", "Place Order", "Pay", "View Order Status", "View Order History")
     * ```
     */
    var behaviors: List<String> = emptyList()

    /**
     * aka property, define a property with name and type, for example:
     * ```kotlin
     * // val name: String
     * prop("name", "String")
     * ```
     */
    @Deprecated("use behavior instead")
    fun prop(name: String, type: String) {
        properties.add(Property(name, type))
    }

    /**
     * aka method, define a method with name, return type and parameters, for example:
     * ```kotlin
     * // fun placeOrder(order: Order): Order
     * method("placeOrder", "Order") {
     *    parameter("order", "Order")
     * }
     * ```
     */
    @Deprecated("use behavior instead")
    fun method(name: String, returnType: String, block: MethodBuilder.() -> Unit) {
        val methodBuilder = MethodBuilder(name, returnType)
        methodBuilder.block()
        methods.add(methodBuilder.
        build())
    }

    fun build(): Class {
        return Class(packageName, className, properties, methods)
    }

    @Deprecated("use behavior instead")
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
        formatBehaviors.add(Behavior(action, description))
    }

    /**
     * same to [ConceptDeclaration.behavior]
     */
    fun usecase(action: String, description: String) {
        formatBehaviors.add(Behavior(action, description))
    }

    inner class MethodBuilder(private val name: String, private val returnType: String) {
        private val parameters = mutableListOf<Parameter>()

        fun parameter(name: String, type: String) {
            parameters.add(Parameter(name, type))
        }

        fun build(): Method = Method(name, returnType, parameters)
    }
}

typealias CodeBlock = Any

/**
 * ConceptSpec is a DSL for define a concept
 * We also try to keep the same usage like [use-case-diagram](https://plantuml.com/zh/use-case-diagram)
 */
class ConceptSpec : Spec<String> {
    val concepts: MutableList<Concept> = mutableListOf()

    override fun default(): Spec<String> = defaultSpec()

    override fun toString(): String = "concept { ${concepts.joinToString(", ")} }"

    companion object {
        fun defaultSpec(): ConceptSpec {
            return concepts {
                val customer = Concept("Customer") {
                    behavior("Place Order", "Place an order for a coffee")
                    behaviors = listOf("View Menu", "Place Order", "Pay", "View Order Status", "View Order History")
                }

                val barista = Concept("Barista") {
                    behavior("Make Coffee")
                }

                Concept("cart")

                relations {
                    customer["Place Order"] perform barista
                    customer["View Menu"] perform barista
                    customer["View Order History"] perform barista
                    customer["Custom something"] perform barista
                }
            }
        }
    }

    fun relations(functions: CodeBlock.() -> Unit) = functions()

    fun concept(clazz: String, pkgName: String = "", function: ConceptDeclaration.() -> Unit): ConceptDeclaration {
        val conceptDeclaration = ConceptDeclaration(clazz, pkgName)
        conceptDeclaration.function()
        return conceptDeclaration
    }

    inner class Concept(val conceptName: String, val function: ConceptDeclaration.() -> Unit = {}) {
        var innerBehaviors = mutableListOf<Behavior>()
        val relations: MutableList<Pair<Behavior, Concept>> = mutableListOf()

        init {
            val concept = ConceptDeclaration(conceptName)
            concept.function()
            this.innerBehaviors = concept.formatBehaviors
            concepts += this
        }

        operator fun get(actionName: String): ConceptAction = ConceptAction(this, actionName)

        fun recordingRelation(behavior: String, target: Concept) {
            val usedBehavior = innerBehaviors.filter { it.action == behavior }.toMutableList()
            if (usedBehavior.isEmpty()) {
                val newBehavior = Behavior(behavior)
                this.innerBehaviors += newBehavior
                usedBehavior += newBehavior
            }

            relations += usedBehavior[0] to target
        }

        override fun toString(): String {
            return """$conceptName { ${innerBehaviors.joinToString(", ")} }"""
        }
    }

    class ConceptAction(val concept: Concept, val actionName: String) {
        infix fun perform(target: Concept) {
            concept.recordingRelation(actionName, target)
        }

        override fun toString(): String = """${concept.conceptName}."$actionName""""
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
