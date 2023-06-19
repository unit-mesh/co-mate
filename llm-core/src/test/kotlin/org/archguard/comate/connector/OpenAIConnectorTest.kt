package org.archguard.comate.connector

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class OpenAIConnectorTest {
    @Test
    fun should_create_similar_prompt_text() {
        val aiConnector = OpenAIConnector("openAiKey", "", "")
        val input = "Introduce the following system: https://github.com/archguard/ddd-monolithic-code-sample"
        val output = aiConnector.promptTemplate(listOf(BaseTool("introduce_system", "introduce_system is a function to introduce a system.")), input)

        assertEquals(output, """Answer the following questions as best you can. You have access to the following tools:

introduce_system: introduce_system is a function to introduce a system.

Use the following format:

Question: the input question you must answer
Thought: you should always think about what to do
Action: the action to take, should be one of [introduce_system]
Action Input: the input to the action
Observation: the result of the action
... (this Thought/Action/Action Input/Observation can repeat N times)
Thought: I now know the final answer
Final Answer: the final answer to the original input question

Begin!

Question: Introduce the following system: https://github.com/archguard/ddd-monolithic-code-sample""")
    }
}