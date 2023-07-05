package org.archguard.spec.lang

import org.archguard.spec.lang.base.Spec
import org.archguard.spec.lang.domain.declaration.ContextMapDeclaration

@SpecDsl
class DomainSpec : Spec<Any> {
    fun context_map(name: String, block: ContextMapDeclaration.() -> Unit): ContextMapDeclaration {
        val contextMapDeclaration = ContextMapDeclaration(name)
        contextMapDeclaration.block()
        return contextMapDeclaration
    }

    override fun default(): Spec<Any> = defaultSpec()

    companion object {
        fun defaultSpec(): DomainSpec =
            domain {
                context_map("TicketBooking") {
                    context("Reservation") {}
                    context("Ticket") {}

                    mapping {
                        context("Reservation") dependedOn context("Ticket")
                        context("Reservation") dependedOn context("Movie")
                    }
                }
            }
    }
}

/**
 * Domain DSL is a DDD style's concept map.
 */
fun domain(init: DomainSpec.() -> Unit): DomainSpec {
    val spec = DomainSpec()
    spec.init()
    return spec
}
