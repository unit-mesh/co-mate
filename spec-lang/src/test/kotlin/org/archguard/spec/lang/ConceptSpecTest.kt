package org.archguard.spec.lang

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class ConceptSpecTest {
    @Test
    fun should_equal_when_had_same_spec() {
        val spec = ConceptSpec.defaultSpec()
        assertEquals(ConceptSpec.defaultSpec().toString(), spec.toString())
    }

    @Test
    fun should_ident_concept_from_spec() {
        val spec = concepts {
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

                customer["Custom something"].perform(barista)
            }
        }

        spec.concepts.size shouldBe 3
        val customer = spec.concepts[0]
        customer.conceptName shouldBe "Customer"
        customer.innerBehaviors.size shouldBe 4
        println(customer.toString())

        val secondConcept = spec.concepts[1]
        secondConcept.conceptName shouldBe "Barista"
        println(secondConcept.toString())
    }
}