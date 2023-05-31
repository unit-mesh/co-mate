package data.remote

import com.theokanning.openai.completion.chat.ChatCompletionRequest
import com.theokanning.openai.completion.chat.ChatMessage
import com.theokanning.openai.service.OpenAiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.withContext
import model.TextCompletionsParam
import kotlin.String


class OpenAIRepositoryImpl {
    private var token: String = ""
//
//    init {
//        val dotenv = Dotenv.load()
//        token = dotenv["OPENAI_API_KEY"]
//    }

    fun textCompletionsWithStream(params: TextCompletionsParam): Flow<String> {
        val service = OpenAiService(token)
        val messages: MutableList<ChatMessage> = mutableListOf()

        params.messages.forEach {
            messages += ChatMessage(it.role.toString(), it.content)
        }

        messages += ChatMessage("user", params.promptText)

        val chatCompletionRequest = ChatCompletionRequest
            .builder()
            .model(params.model.model)
            .messages(messages)
            .n(params.n)
            .maxTokens(params.maxTokens)
            .logitBias(HashMap())
            .build()

        return callbackFlow {
            withContext(Dispatchers.IO) {
                service.streamChatCompletion(chatCompletionRequest)
                    .doOnError(Throwable::printStackTrace)
                    .blockingForEach { response ->
                        val completion = response.choices[0].message
                        if (completion != null && completion.content != null) {
                            trySend(completion.content)
                        }
                    }

                service.shutdownExecutor()

                close()
            }
        }
    }
}