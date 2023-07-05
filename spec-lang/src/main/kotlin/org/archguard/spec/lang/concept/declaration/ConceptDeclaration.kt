package org.archguard.spec.lang.concept.declaration

import org.archguard.spec.lang.concept.Behavior

/**
 * Concept is a class abstraction for a concept, will be used to generate code.
 */
class ConceptDeclaration(private val className: String, private val packageName: String = "") {
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
}