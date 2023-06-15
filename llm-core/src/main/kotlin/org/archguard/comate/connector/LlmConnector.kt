package org.archguard.comate.connector

import kotlinx.coroutines.flow.Flow
import java.time.Duration

interface LlmConnector {
    val timeout: Duration
        get() = Duration.ofSeconds(600)

    fun prompt(promptText: String): String
    fun stream(promptText: String): Flow<String>
}