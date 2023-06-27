package org.archguard.comate.server.action.dto

import kotlinx.serialization.Serializable

@Serializable
data class ActionResult(val status: String, val action: String)