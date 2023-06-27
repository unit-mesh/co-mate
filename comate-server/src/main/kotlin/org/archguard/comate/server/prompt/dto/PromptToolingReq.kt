package org.archguard.comate.server.prompt.dto

import kotlinx.serialization.Serializable

@Serializable
data class PromptToolingReq(val text: String)