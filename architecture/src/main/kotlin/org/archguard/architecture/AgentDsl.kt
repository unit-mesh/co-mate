package org.archguard.architecture

import org.archguard.architecture.layered.ArchStyle

class ComateDsl {
    private var workdir: String = ""
    private var archStyle: ArchStyle = ArchStyle.MVC

    fun workspace(path: String) {
        this.workdir = path
    }

    /**
     * the Architecture Style, default is MVC, available values are: [ArchStyle.valuesString]
     * should be lowercase
     */
    fun archStyle(string: String) {
        if (ArchStyle.contains(string)) {
            this.archStyle = ArchStyle.valueOf(string.uppercase())
        }
    }

    fun group(packageName: String) {

    }

    fun operation(operation: Operation.() -> Unit): Operation {
        val op = Operation()
        op.operation()
        return op
    }
}

class Operation {
    fun createClass(packageName: String, className: String, body: String) {
        // todo: java poet or kotlin poet ?
    }
}

class ClassBody {
    fun createMethod(methodName: String, returnType: String, body: String) {

    }
}

fun exec(init: ComateDsl.() -> Unit): ComateDsl {
    val agent = ComateDsl()
    agent.init()
    return agent
}
