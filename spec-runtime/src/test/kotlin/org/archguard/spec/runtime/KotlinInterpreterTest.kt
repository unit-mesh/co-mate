package org.archguard.spec.runtime

import io.kotest.matchers.shouldBe
import org.archguard.spec.runtime.interpreter.api.InterpreterRequest
import org.archguard.spec.runtime.messaging.MessageType
import org.junit.jupiter.api.Test

class KotlinInterpreterTest {
    @Test
    fun should_return_correct_message_from_kotlin_interpreter() {
        val interpreter = KotlinInterpreter()
        val message = interpreter.eval(InterpreterRequest(code = "1 + 2"))

        message.resultValue shouldBe "3"
        message.className shouldBe "java.lang.Integer"
        message.msgType shouldBe MessageType.NONE
        message.content shouldBe null
    }

    @Test
    fun should_cast_to_int() {
        val interpreter = KotlinInterpreter()
        val result = interpreter.evalCast<Int>(InterpreterRequest(code = "1 + 2"))
        result shouldBe 3
    }
}