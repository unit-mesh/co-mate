package org.archguard.comate.token

import com.knuddels.jtokkit.Encodings
import com.knuddels.jtokkit.api.Encoding
import com.knuddels.jtokkit.api.EncodingRegistry
import com.knuddels.jtokkit.api.ModelType


class LlmTokenCalculate(override val maxToken: Int, val modelType: String) : TokenCalculate {
    var registry: EncodingRegistry = Encodings.newDefaultEncodingRegistry()

    override fun calculate(input: String): Int {
        val modelType = ModelType.fromName(modelType)
        if (modelType.isPresent) {
            val enc: Encoding = registry.getEncodingForModel(modelType.get())
            return enc.encode(input).size
        }

        throw IllegalArgumentException("Model type $modelType not found")
    }
}