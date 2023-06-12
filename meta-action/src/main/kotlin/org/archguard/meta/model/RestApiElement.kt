package org.archguard.meta.model

import kotlinx.serialization.json.JsonObject
import org.archguard.meta.base.Element

data class RestApiElement(
    val uri: String,
    val httpAction: String,
    val statusCodes: List<Int>,
    val request: JsonObject? = null,
    val response: List<JsonObject> = listOf(),
) : Element {
    override fun toString(): String {
        return "RestApi(uri='$uri', action='$httpAction', statusCodes=$statusCodes, request=$request, response=$response)"
    }
}