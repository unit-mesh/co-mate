package org.archguard.comate.smart

import com.theokanning.openai.OpenAiApi
import com.theokanning.openai.completion.chat.ChatCompletionRequest
import com.theokanning.openai.completion.chat.ChatMessage
import com.theokanning.openai.completion.chat.ChatMessageRole
import com.theokanning.openai.service.OpenAiService
import com.theokanning.openai.service.OpenAiService.defaultClient
import com.theokanning.openai.service.OpenAiService.defaultObjectMapper
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.jackson.JacksonConverterFactory
import java.time.Duration

val OPENAI_MODEL = arrayOf("gpt-3.5-turbo", "gpt-4.0")

class OpenAIConnector(
    val openAiKey: String,
    val openAiVersion: String = OPENAI_MODEL[0],
    val openAiProxy: String? = null,
): LlmConnector {
    private var service: OpenAiService

    init {
        if (openAiKey.isEmpty()) {
            throw Exception("openAiKey is empty")
        }

        if (openAiProxy.isNullOrEmpty()) {
            service = OpenAiService(openAiKey, timeout)
        } else {
            val mapper = defaultObjectMapper()
            val client = defaultClient(openAiKey, timeout)

            val retrofit = Retrofit.Builder()
                .baseUrl(openAiProxy)
                .client(client)
                .addConverterFactory(JacksonConverterFactory.create(mapper))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()

            val api = retrofit.create(OpenAiApi::class.java)
            service = OpenAiService(api)
        }
    }

    val messages: MutableList<ChatMessage> = ArrayList()
    var historyMessageLength: Int = 0

    override fun prompt(promptText: String): String {
        val systemMessage = ChatMessage(ChatMessageRole.USER.value(), promptText)

        historyMessageLength += promptText.length

        // todo: 4096 is the max length of history message, need to find a better way to handle thiss
        if (historyMessageLength > 4096) {
            messages.clear()
        }

        messages.add(systemMessage)

        val completionRequest = ChatCompletionRequest.builder()
            .model(openAiVersion)
            .temperature(0.0)
            .messages(messages)
            .build()

        val completion = service.createChatCompletion(completionRequest)
        val output = completion
            .choices[0].message.content

        return output
    }
}