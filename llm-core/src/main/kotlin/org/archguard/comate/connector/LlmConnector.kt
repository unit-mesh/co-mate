package org.archguard.comate.connector

import kotlinx.coroutines.flow.Flow
import java.time.Duration

class BaseTool(val name: String, val description: String)

interface LlmConnector {
    val timeout: Duration
        get() = Duration.ofSeconds(600)

    fun prompt(promptText: String): String
    fun stream(promptText: String): Flow<String>
    fun promptTemplate(tools: List<BaseTool>, input: String): String
}