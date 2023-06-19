package org.archguard.comate.connector

import com.theokanning.openai.OpenAiApi
import com.theokanning.openai.completion.chat.ChatCompletionRequest
import com.theokanning.openai.completion.chat.ChatMessage
import com.theokanning.openai.completion.chat.ChatMessageRole
import com.theokanning.openai.service.OpenAiService
import com.theokanning.openai.service.OpenAiService.defaultClient
import com.theokanning.openai.service.OpenAiService.defaultObjectMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.jackson.JacksonConverterFactory

val OPENAI_MODEL = arrayOf("gpt-3.5-turbo", "gpt-4.0")

class OpenAIConnector(
    private val openAiKey: String,
    private val openAiVersion: String = OPENAI_MODEL[0],
    private val openAiProxy: String? = null,
) : LlmConnector {
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
    private var historyMessageLength: Int = 0

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

    override fun stream(promptText: String): Flow<String> {
        val systemMessage = ChatMessage(ChatMessageRole.USER.value(), promptText)

        messages.add(systemMessage)

        val completionRequest = ChatCompletionRequest.builder()
            .model(openAiVersion)
            .temperature(0.0)
            .messages(messages)
            .build()

        return callbackFlow {
            withContext(Dispatchers.IO) {
                service.streamChatCompletion(completionRequest)
                    .doOnError(Throwable::printStackTrace)
                    .blockingForEach { response ->
                        val completion = response.choices[0].message
                        if (completion != null && completion.content != null) {
                            trySend(completion.content)
                        }
                    }

                close()
            }
        }
    }

    val PREFIX = """Answer the following questions as best you can. You have access to the following tools:"""
    val FORMAT_INSTRUCTIONS = """Use the following format:

    Question: the input question you must answer
    Thought: you should always think about what to do
    Action: the action to take, should be one of [{tool_names}]
    Action Input: the input to the action
    Observation: the result of the action
    ... (this Thought/Action/Action Input/Observation can repeat N times)
    Thought: I now know the final answer
    Final Answer: the final answer to the original input question"""
    val SUFFIX = """Begin!

    Question: {input}
    Thought:{agent_scratchpad}"""

    override fun chain(tools: List<BaseTool>, inputVariables: List<String>): PromptTemplate {
        val toolStrings = tools.joinToString("\n") { "${it.name}: ${it.description}" }
        val toolNames = tools.joinToString(", ") { it.name }
        val formatInstructions = FORMAT_INSTRUCTIONS.format(toolNames)
        val template = "$PREFIX\n\n$toolStrings\n\n$formatInstructions\n\n$SUFFIX"

        // todo: spike for elements
        val variables = inputVariables.toMutableList()
        if (variables.isEmpty()) {
            variables.add("input")
            variables.add("agent_scratchpad")
        }

        return PromptTemplate(template, variables)
    }
}
