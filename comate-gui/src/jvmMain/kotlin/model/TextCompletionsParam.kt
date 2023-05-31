package model

import com.google.gson.JsonArray
import com.google.gson.JsonObject
import kotlinx.serialization.Serializable

@Serializable
data class TextCompletionsParam(
    val promptText: String = "",
    val temperature: Double = 0.9,
    val top_p: Double = 1.0,
    val n: Int = 1,
    var stream: Boolean = true,
    val maxTokens: Int = 2048,
    val model: GPTModel = GPTModel.gpt35Turbo,
    val messages: List<MessageTurbo> = emptyList(),
) {
    val isTurbo: Boolean
        get() = model == GPTModel.gpt35Turbo
}

fun TextCompletionsParam.toJson(): JsonObject {
    val json = JsonObject()
    json.addProperty("temperature", temperature)
    json.addProperty("stream", stream)
    json.addProperty("model", model.model)

    if (model == GPTModel.gpt35Turbo) {
        val jsonArray = JsonArray()
        for (message in messages) jsonArray.add(message.toJson())

        json.add("messages", jsonArray)
    } else {
        json.addProperty("prompt", promptText)
    }

    return json
}