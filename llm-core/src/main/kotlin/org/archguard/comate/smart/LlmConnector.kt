package org.archguard.comate.smart

import java.time.Duration

interface LlmConnector {
    val timeout: Duration
        get() = Duration.ofSeconds(600)

    fun prompt(promptText: String): String
}