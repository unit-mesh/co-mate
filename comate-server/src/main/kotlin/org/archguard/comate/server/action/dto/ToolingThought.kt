package org.archguard.comate.server.action.dto

import kotlinx.serialization.Serializable

@Serializable
data class ToolingThought(val thought: String, val action: String, val actionInput: String)