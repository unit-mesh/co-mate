package data.remote

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.withContext
import model.TextCompletionsParam

class OpenAIRepositoryImpl {
    fun textCompletionsWithStream(textCompletionsParam: TextCompletionsParam): Flow<String> {
        return callbackFlow {
            withContext(Dispatchers.IO) {
                trySend("It works!!! Todo: send to OpenAI Server")
                close()
            }
        }
    }
}