package org.archguard.meta.restful

import kotlinx.serialization.json.JsonObject
import org.archguard.meta.Element

data class RestApi(
    val uri: String,
    val action: String,
    val statusCodes: List<Int>,
    val request: JsonObject? = null,
    val response: List<JsonObject> = listOf(),
) : Element() {
    override fun toString(): String {
        return "RestApi(uri='$uri', action='$action', statusCodes=$statusCodes, request=$request, response=$response)"
    }
}