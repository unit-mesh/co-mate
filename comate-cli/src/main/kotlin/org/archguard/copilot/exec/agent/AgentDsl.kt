package org.archguard.copilot.exec.agent

enum class ArchStyle(val value: String) {
    MVC("mvc"),
    MVP("mvp"),
    MVVM("mvvm"),
    VIPER("viper"),
    CLEAN("clean"),
    DDD("ddd"),
    ONION("onion"),
    HEXAGONAL("hexagonal");

    companion object {
        fun contains(string: String): Boolean {
            val lowercase = string.lowercase()
            return values().any { it.value == lowercase }
        }

        fun valuesString(): String {
            return values().joinToString(", ") { it.value }
        }
    }
}

class ExecAgent {
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

fun exec(init: ExecAgent.() -> Unit): ExecAgent {
    val agent = ExecAgent()
    agent.init()
    return agent
}
