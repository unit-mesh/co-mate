package org.archguard.comate.server.prompt.model

import kotlinx.serialization.Serializable

@Serializable
class BaseTool(val name: String, val description: String)