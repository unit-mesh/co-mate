package org.archguard.comate.dynamic.functions

import org.archguard.architecture.style.NamingStyle

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.CLASS)
annotation class ComateFunction

/**
 * A dynamic function is a function that can be executed by the dynamic context
 * and can be defined by the LLM.
 *
 * 1. it needs to be annotated with @ComateFunction
 * 2. Should have default constructor with no parameters
 */
interface DyFunction {
    fun explain(): String
    /**
     * return true if the function is executed successfully
     */
    fun execute(): Boolean

    /**
     * function definition for LLM to select the best function
     */
    fun define(): String {
        val pureJavaName = this.javaClass.name.split(".").last()
        val functionName = NamingStyle.toSnakeCase(pureJavaName).replace("_function", "")

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