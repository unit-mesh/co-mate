package model

import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.google.gson.annotations.SerializedName

data class TextCompletionsParam(
    @SerializedName("prompt")
    val promptText: String = "",
    @SerializedName("temperature")
    val temperature: Double = 0.9,
    @SerializedName("top_p")
    val topP: Double = 1.0,
    @SerializedName("n")
    val n: Int = 1,
    @SerializedName("stream")
    var stream: Boolean = true,
    @SerializedName("maxTokens")
    val maxTokens: Int = 2048,
    @SerializedName("model")
    val model: GPTModel = GPTModel.gpt35Turbo,
    @SerializedName("messages")
    val messagesTurbo: List<MessageTurbo> = emptyList(),
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
        for (message in messagesTurbo) jsonArray.add(message.toJson())

        json.add("messages", jsonArray)
    } else {
        json.addProperty("prompt", promptText)
    }

    return json
}