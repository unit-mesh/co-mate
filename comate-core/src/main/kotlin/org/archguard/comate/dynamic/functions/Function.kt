package org.archguard.comate.dynamic.functions

import org.archguard.architecture.style.NamingStyle

interface Function {
    /**
     * return true if the function is executed successfully
     */
    fun execute(): Boolean
    fun define(): String {
        val pureJavaName = this.javaClass.name.split(".").last()
        val functionName = NamingStyle.toSnakeCase(pureJavaName)

        val params = this.parameters()
        val paramsString = params.map {
            "${it.key}: ${it.value}"
        }.joinToString(", ")

        return "$functionName($paramsString)"
    }

    fun parameters(): HashMap<String, String>
}