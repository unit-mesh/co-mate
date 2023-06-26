package org.archguard.spec.runtime

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import io.kotest.matchers.shouldBe
import org.archguard.spec.runtime.compiler.KotlinReplWrapper
import org.archguard.spec.runtime.interpreter.api.InterpreterRequest
import org.archguard.spec.runtime.messaging.MessageType

class KotlinInterpreterTest {
    private lateinit var compiler: KotlinReplWrapper

    @BeforeEach
    internal fun setUp() {
        this.compiler = KotlinReplWrapper()
    }

    @Test
    internal fun simple_eval() {
        compiler.eval("val x = 3")
        val res = compiler.eval("x*2")
        res.rawValue shouldBe 6
    }

    @Test
    fun should_return_correct_message_from_kotlin_interpreter() {
        val interpreter = KotlinInterpreter()
        val message = interpreter.eval(InterpreterRequest(code = "1 + 2"))

        message.resultValue shouldBe "3"
        message.className shouldBe "java.lang.Integer"
        message.msgType shouldBe MessageType.NONE
        message.content shouldBe null
    }
}