package org.archguard.comate.server.prompt.dto

import kotlinx.serialization.Serializable
import org.archguard.comate.server.prompt.model.BaseTool

@Serializable
data class PromptToolingRes(val prompt: String, val tools: List<BaseTool>)