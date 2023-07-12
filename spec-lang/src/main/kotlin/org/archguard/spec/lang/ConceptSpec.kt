package org.archguard.spec.lang

import org.archguard.spec.lang.base.Spec
import org.archguard.spec.lang.concept.Behavior
import org.archguard.spec.lang.concept.CodeBlock
import org.archguard.spec.lang.concept.declaration.ConceptDeclaration

/**
 * ConceptSpec is a DSL for define a concept
 * We also try to keep the same usage like [use-case-diagram](https://plantuml.com/zh/use-case-diagram)
 */
@SpecDsl
class ConceptSpec : Spec<String> {
    val concepts: MutableList<Concept> = mutableListOf()

    override fun default(): Spec<String> = defaultSpec()

    override fun toString(): String = "concept { ${concepts.joinToString(", ")} }"

    override fun example(): String =
        concepts {
            val customer = Concept("Customer") {

            }
            val cart = Concept("Shopping Cart") {
                behavior("Add to Cart", "Add a coffee to the shopping cart")
            }

            relations {
                customer["Add to Cart"] to cart
            }
        }.toString()

    companion object {
        fun defaultSpec(): ConceptSpec =
            concepts {
                val customer = Concept("Customer") {
                    behavior("Place Order", "Place an order for a coffee")
                    behaviors = listOf(
                        "View Menu",
                        "Add to Cart",
                        "Remove from Cart",
                        "Place Order",
                        "Pay",
                        "View Order Status",
                        "View Order History",
                        "Customize Order"
                    )
                }

                val barista = Concept("Barista") {
                    behavior("Make Coffee")
                }

                val deliveryPerson = Concept("Delivery Person") {
                    behavior("Deliver Order")
                }

                val shoppingCart = Concept("Shopping Cart") {
                    behavior("Add to Cart", "Add a coffee to the shopping cart")
                    behavior("Remove from Cart", "Remove a coffee from the shopping cart")
                    behavior("View Cart", "View the contents of the shopping cart")
                    behavior("Checkout", "Proceed to checkout and place the order")
                }

                relations {
                    customer["View Menu"] perform barista
                    customer["View Order History"] perform barista

                    customer["Add to Cart"] perform shoppingCart
                    customer["Remove from Cart"] perform shoppingCart
                    customer["View Cart"] perform shoppingCart

                    customer["Checkout"] perform barista
                    customer["Place Order"] perform barista

                    customer["Pay"] perform deliveryPerson
                    customer["View Order Status"] perform deliveryPerson
                    customer["Customize Order"].perform(barista) {
                        // condition("").action("") // when need
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
        var behaviors = mutableListOf<Behavior>()
        val relations: MutableList<Pair<Behavior, Concept>> = mutableListOf()

        init {
            val concept = ConceptDeclaration(conceptName)
            concept.function()
            this.behaviors = concept.formatBehaviors
            concepts += this
        }

        operator fun get(actionName: String): ConceptAction = ConceptAction(this, actionName)

        fun recordingRelation(behavior: String, target: Concept) {
            val usedBehavior = behaviors.filter { it.action == behavior }.toMutableList()
            if (usedBehavior.isEmpty()) {
                val newBehavior = Behavior(behavior)
                this.behaviors += newBehavior
                usedBehavior += newBehavior
            }

            relations += usedBehavior[0] to target
        }

        override fun toString(): String {
            return """$conceptName { ${behaviors.joinToString(", ")} }"""
        }
    }

    class ConceptAction(val concept: Concept, val actionName: String) {
        infix fun perform(target: Concept) {
            concept.recordingRelation(actionName, target)
        }

        fun perform(target: Concept, function: RuleDeclaration.() -> Unit): RuleDeclaration {
            concept.recordingRelation(actionName, target)
            val ruleDeclaration = RuleDeclaration(actionName)
            ruleDeclaration.function()
            return ruleDeclaration
        }

        override fun toString(): String = """${concept.conceptName}."$actionName""""
    }
}

class RuleDeclaration(val name: String) {
    private val conditions = mutableListOf<String>()
    private var action: String = ""

    fun condition(condition: String): RuleDeclaration {
        conditions.add(condition)
        return this
    }

    fun action(action: String): RuleDeclaration {
        this.action = action
        return this
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
