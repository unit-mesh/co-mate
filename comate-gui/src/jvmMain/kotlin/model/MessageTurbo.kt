package model

import com.google.gson.JsonObject
import kotlinx.serialization.Serializable

@Serializable
data class MessageTurbo(
    val content: String = "",
    val role: TurboRole = TurboRole.user,
)

fun MessageTurbo.toJson() : JsonObject {
    val json = JsonObject()
    json.addProperty("content", content)
    json.addProperty("role", role.value)

    return json
}