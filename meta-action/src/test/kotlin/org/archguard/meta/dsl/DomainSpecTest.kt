package org.archguard.meta.dsl

import org.junit.jupiter.api.Test

class DomainSpecTest {
    @Test
    fun testContextMap() {
        val spec = domain {
            context_map("TicketBooking") {
                context("Reservation") {
                    aggregate("") {
                        // TODO
                    }
                }
            }
        }
    }
}