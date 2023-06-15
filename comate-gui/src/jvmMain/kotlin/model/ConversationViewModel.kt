package model

import cafe.adriel.voyager.core.model.ScreenModel
import data.remote.OpenAIRepositoryImpl
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.*

class ConversationViewModel(
    private val openAIRepo: OpenAIRepositoryImpl,
) : ScreenModel {
    private val _currentConversation: MutableStateFlow<String> =
        MutableStateFlow(Date().time.toString())
    private val _messages: MutableStateFlow<HashMap<String, MutableList<MessageModel>>> =
        MutableStateFlow(HashMap())
    private val _isFetching: MutableStateFlow<Boolean> = MutableStateFlow(false)

    val currentConversationState: StateFlow<String> = _currentConversation.asStateFlow()
    val messagesState: StateFlow<HashMap<String, MutableList<MessageModel>>> = _messages.asStateFlow()
    val isFetching: StateFlow<Boolean> = _isFetching.asStateFlow()

    private val _conversations: MutableStateFlow<MutableList<ConversationModel>> = MutableStateFlow(
        mutableListOf()
    )

    private fun createConversationRemote(title: String) {
        val newConversation = ConversationModel(
            id = _currentConversation.value,
            title = title,
            createdAt = Date(),
        )

        val conversations = _conversations.value.toMutableList()
        conversations.add(0, newConversation)

        _conversations.value = conversations
    }

    suspend fun sendMessage(message: String) {
        // todo: maintain conversation in local
        createConversationRemote(message)

        val newMessageModel = MessageModel(
            question = message,
            answer = "Let me thinking...",
            conversationId = _currentConversation.value,
        )

        val flow: Flow<String> = openAIRepo.textCompletionsWithStream(
            TextCompletionsParam(
                promptText = newMessageModel.question,
            )
        )

        var answerFromGPT = ""

        flow.collect {
            answerFromGPT += it
            updateLocalAnswer(message, answerFromGPT.trim())
        }
    }

    private fun updateLocalAnswer(question: String, answer: String) {
        val currentListMessage: MutableList<MessageModel> = mutableListOf()
        currentListMessage.add(
            MessageModel(
                question = question,
                answer = answer,
                conversationId = _currentConversation.value,
            )
        )

        setMessages(currentListMessage)
    }

    private fun setMessages(messages: MutableList<MessageModel>) {
        val messagesMap: HashMap<String, MutableList<MessageModel>> =
            _messages.value.clone() as HashMap<String, MutableList<MessageModel>>

        messagesMap[_currentConversation.value] = messages

        _messages.value = messagesMap
    }
}