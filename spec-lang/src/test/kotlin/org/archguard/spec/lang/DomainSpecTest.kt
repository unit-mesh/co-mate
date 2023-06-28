package org.archguard.spec.lang

import org.junit.jupiter.api.Test

class DomainSpecTest {
    private val governance = domain {
        context_map("TicketBooking") {
            context("Reservation") {}
            context("Ticket") {}

            mapping {
                context("Reservation") dependedOn context("Ticket")
                context("Reservation") dependedOn context("Movie")
            }
        }
    }

    private val userStory = user_story {
        Feature("", tag = "") {
            Scenario("") {
                Given("")
                And("")
                When("")
                Then("")
            }
        }
    }

    @Test
    fun testContextMap() {
        governance.exec("")
        userStory.exec("")
    }
}