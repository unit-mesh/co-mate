package org.archguard.comate.server.prompt

import kotlinx.serialization.Serializable

@Serializable
class BaseTool(val name: String, val description: String)

class PromptingWrapper {
    val PREFIX = """Answer the following questions as best you can. You have access to the following tools:"""
    private fun formatInstructions(toolNames: String) = """
    Use the following format:

    Question: the input question you must answer
    Thought: you should always think about what to do
    Action: the action to take, should be one of [${toolNames}]
    Action Input: the input to the action
    Observation: the result of the action
    ... (this Thought/Action/Action Input/Observation can repeat N times)
    Thought: I now know the final answer
    Final Answer: the final answer to the original input question""".trimIndent()

    private fun functionsInstruction(toolNames: String) = """
    Use the following format:

    Question: the input question you must answer
    Thought: you should always think about what to do
    Action: the action to take, should be one of [${toolNames}]
    Action Input: the input to the action
    """.trimIndent()

    private fun suffix(input: String) = """
    Begin!

    Question: $input
    """.trimIndent()

    fun defaultChain(text: String, tools: List<BaseTool>): String {
        val toolStrings = tools.joinToString("\n") { "${it.name}: ${it.description}" }
        val toolNames = tools.joinToString(", ") { it.name }
        val formatInstructions = formatInstructions(toolNames)

        return """$PREFIX

$toolStrings

$formatInstructions

${suffix(text)}"""
    }

    fun functionSearch(text: String, tools: List<BaseTool>): String {
        val toolStrings = tools.joinToString("\n") { "${it.name}: ${it.description}" }
        val toolNames = tools.joinToString(", ") { it.name }
        val formatInstructions = functionsInstruction(toolNames)

        return """$PREFIX

$toolStrings

$formatInstructions

${suffix(text)}"""
    }
}