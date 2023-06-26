package org.archguard.comate.code

import chapi.domain.core.CodeCall
import chapi.domain.core.CodeDataStruct
import chapi.domain.core.CodeFunction
import org.junit.jupiter.api.Assertions.*

class FunctionCallChainTest {
    @org.junit.jupiter.api.Test
    fun should_return_correct() {
        val source = CodeDataStruct(
            Package = "org.archguard",
            NodeName = "Source",
            Functions = listOf(
                CodeFunction(
                    Name = "func1",
                    Parameters = listOf(),
                    FunctionCalls = listOf(
                        CodeCall(
                            Package = "org.archguard",
                            NodeName = "Target",
                            FunctionName = "func2",
                            Parameters = listOf(),
                        ),
                    ),
                )
            ),
        )

        val target = CodeDataStruct(
            Package = "org.archguard",
            NodeName = "Target",
            Functions = listOf(
                CodeFunction(
                    Name = "func2",
                    Parameters = listOf(),
                )
            ),
        )

        val result = FunctionCallChain().analysis("org.archguard.Source.func1", listOf(source, target))
        assertEquals("org.archguard.Source.func1 -> org.archguard.Target", result.toString())
    }
}