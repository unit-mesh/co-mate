package org.archguard.spec.runtime

import org.archguard.spec.runtime.interpreter.api.InterpreterRequest
import org.archguard.spec.runtime.messaging.ErrorContent
import org.archguard.spec.runtime.messaging.Message
import org.archguard.spec.runtime.messaging.MessageType
import org.archguard.spec.runtime.compiler.KotlinReplWrapper
import org.jetbrains.kotlinx.jupyter.repl.EvalResultEx
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class KotlinInterpreter {
    private var compiler: KotlinReplWrapper = KotlinReplWrapper()
    private val logger: Logger = LoggerFactory.getLogger(this.javaClass)

    fun eval(request: InterpreterRequest): Message {
        return try {
            val resultEx = compiler.eval(request.code, request.id, request.history)
            convertResult(resultEx, request.id)
        } catch (e: Exception) {
            logger.error(e.toString())
            val content = ErrorContent(e.javaClass.name, e.toString())
            Message(request.id, "", "", "", MessageType.ERROR, content = content)
        }
    }

    fun <T> evalCast(request: InterpreterRequest): T? {
        return try {
            val resultEx = compiler.eval(request.code, request.id, request.history)
            val className: String = resultEx.rawValue?.javaClass?.name.orEmpty()
            resultEx.rawValue as T
        } catch (e: Exception) {
            logger.error(e.toString())
            return null
        }
    }

    private fun convertResult(result: EvalResultEx, id: Int): Message {
        val resultValue = result.rawValue
        val className: String = resultValue?.javaClass?.name.orEmpty()

        return Message(
            id,
            className = className,
            resultValue = resultValue.toString(),
            displayValue = result.rawValue.toString()
        )
    }
}