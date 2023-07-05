package org.archguard.spec.lang.architecture

import org.archguard.spec.lang.base.BaseDeclaration

class ConnectionDeclaration(val source: String, val target: String) : BaseDeclaration<String>

class SystemDeclaration(val name: String) : BaseDeclaration<String> {
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

class ComponentDeclaration(val name: String) : BaseDeclaration<String> {
    fun module(moduleName: String, function: ModuleDeclaration.() -> Unit): ModuleDeclaration {
        val module = ModuleDeclaration(moduleName)
        module.function()
        return module
    }
}

class ModuleDeclaration(val name: String) : BaseDeclaration<String>