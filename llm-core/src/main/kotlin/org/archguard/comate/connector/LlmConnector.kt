package org.archguard.comate.connector

import kotlinx.coroutines.flow.Flow
import java.time.Duration

class BaseTool(val name: String, val description: String)

class PromptTemplate(val template: String, val inputVariables: List<String>) {
    override fun toString(): String {
        var result = template
        inputVariables.forEachIndexed { index, variable ->
            result = result.replace("{{${index}}}", variable)
        }

        return result
    }

}

interface LlmConnector {
    val timeout: Duration
        get() = Duration.ofSeconds(600)

    fun prompt(promptText: String): String
    fun stream(promptText: String): Flow<String>
    fun chain(tools: List<BaseTool>, inputVariables: List<String> = listOf()): PromptTemplate
}