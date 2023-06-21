package org.archguard.comate.dynamic.functions

import com.fasterxml.jackson.annotation.JsonValue
import kotlinx.serialization.Serializable
import org.archguard.architecture.style.NamingStyle
import org.archguard.comate.command.ComateContext

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.CLASS)
annotation class ComateFunction

@Serializable
sealed class FunctionResult<out T : Any> {
    @Serializable
    class Success<out T : Any>(val value: T) : FunctionResult<T>()

    @Serializable
    class Failure(val message: String) : FunctionResult<String>()
}

/**
 * A dynamic function is a function that can be executed by the dynamic context
 * and can be defined by the LLM.
 *
 * 1. it needs to be annotated with @ComateFunction
 * 2. Should have default constructor with no parameters
 */
interface DyFunction {
    val context: ComateContext
    val hidden: Boolean get() = false

    fun explain(): String

    /**
     * return true if the function is executed successfully
     */
    fun execute(): FunctionResult<Any>

    /**
     * function definition for LLM to select the best function
     */
    fun define(): String {
        val functionName = javaClass.name.toSnakeCase()

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

fun String.toSnakeCase(): String {
    return NamingStyle.toSnakeCase(this.split(".").last()).replace("_function", "")
}