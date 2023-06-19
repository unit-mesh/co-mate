package org.archguard.comate.connector

import kotlinx.coroutines.flow.Flow
import java.time.Duration

class BaseTool(val name: String, val description: String)

interface LlmConnector {
    val timeout: Duration
        get() = Duration.ofSeconds(600)

    fun prompt(text: String): String
    fun stream(text: String): Flow<String>
    fun promptTemplate(text: String, tools: List<BaseTool>): String
}