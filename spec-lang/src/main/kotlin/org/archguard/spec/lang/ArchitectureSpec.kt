package org.archguard.spec.lang

import org.archguard.spec.lang.architecture.SystemDeclaration
import org.archguard.spec.lang.base.RuleSpec
import org.archguard.spec.lang.base.Spec

@SpecDsl
class ArchitectureSpec : RuleSpec<String> {
    override fun default(): Spec<String> = defaultSpec()

    fun system(systemName: String, block: SystemDeclaration.() -> Unit): SystemDeclaration {
        val system = SystemDeclaration(systemName)
        system.block()
        return system
    }

    companion object {
        fun defaultSpec(): ArchitectureSpec {
            return architecture {
                system("TicketBooking") {
                    connection("Reservation" to "Ticket")
                }
            }
        }
    }
}

/**
 * Architecture DSL is a useless DSL for architecture specification.
 */
fun architecture(function: ArchitectureSpec.() -> Unit): ArchitectureSpec {
    val spec = ArchitectureSpec()
    spec.function()
    return spec
}