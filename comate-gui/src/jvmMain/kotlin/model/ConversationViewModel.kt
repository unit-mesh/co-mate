package model

import cafe.adriel.voyager.core.model.ScreenModel
import data.remote.OpenAIRepositoryImpl
import org.kodein.di.DI

class ConversationViewModel(
    private val openAIRepo: OpenAIRepositoryImpl,
) : ScreenModel {
    suspend fun initialize() {

    }

    suspend fun sendMessage(message: String) {
        //
    }
}