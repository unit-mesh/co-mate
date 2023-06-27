package org.archguard.spec.lang

import org.archguard.spec.base.RuleResult
import org.archguard.spec.lang.base.BaseDeclaration
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

class ConnectionDeclaration(val source: String, val target: String) : BaseDeclaration<String>

class SystemDeclaration(name: String) : BaseDeclaration<String> {
    fun component(componentName: String, function: ComponentDeclaration.() -> Unit): ComponentDeclaration {
        val component = ComponentDeclaration(componentName)
        component.function()
        return component
    }

    fun connection(pair: Pair<String, String>, function: ConnectionDeclaration.() -> Unit?): ConnectionDeclaration {
        val connection = ConnectionDeclaration(pair.first, pair.second)
        connection.function()
        return connection
    }

    fun connection(pair: Pair<String, String>): ConnectionDeclaration {
        return ConnectionDeclaration(pair.first, pair.second)
    }

}

class ComponentDeclaration(name: String) : BaseDeclaration<String> {
    fun module(moduleName: String, function: ModuleDeclaration.() -> Unit): ModuleDeclaration {
        val module = ModuleDeclaration(moduleName)
        module.function()
        return module
    }
}

class ModuleDeclaration(name: String) : BaseDeclaration<String>

fun architecture(function: ArchitectureSpec.() -> Unit): ArchitectureSpec {
    val spec = ArchitectureSpec()
    spec.function()
    return spec
}