package org.archguard.spec.lang

import org.archguard.spec.base.RuleResult
import org.archguard.spec.lang.architecture.SystemDeclaration
import org.archguard.spec.lang.base.Spec

class ArchitectureSpec : Spec<String> {
    override fun default(): Spec<String> {
        return defaultSpec()
    }

    override fun exec(element: String): List<RuleResult> {
        return listOf()
    }

    fun system(systemName: String, function: SystemDeclaration.() -> Unit): SystemDeclaration {
        val system = SystemDeclaration(systemName)
        system.function()
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

fun architecture(function: ArchitectureSpec.() -> Unit): ArchitectureSpec {
    val spec = ArchitectureSpec()
    spec.function()
    return spec
}