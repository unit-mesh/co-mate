package org.archguard.comate.dynamic.functions

import org.archguard.architecture.style.NamingStyle

@Target(AnnotationTarget.CLASS)
annotation class ComateFunction

interface DyFunction {
    /**
     * return true if the function is executed successfully
     */
    fun execute(): Boolean

    /**
     * function definition for LLM to select the best function
     */
    fun define(): String {
        val pureJavaName = this.javaClass.name.split(".").last()
        val functionName = NamingStyle.toSnakeCase(pureJavaName)

        val params = this.parameters()
        val paramsString = params.map {
            "${it.key}: ${it.value}"
        }.joinToString(", ")

        return "$functionName($paramsString)"
    }

    /**
     * What parameters does the function need?
     */
    fun parameters(): HashMap<String, String>
}