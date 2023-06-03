package org.archguard.comate

import ai.djl.huggingface.tokenizers.HuggingFaceTokenizer
import ai.onnxruntime.OnnxTensor
import ai.onnxruntime.OrtEnvironment
import ai.onnxruntime.OrtSession
import ai.onnxruntime.OrtSession.SessionOptions
import ai.onnxruntime.OrtUtil
import org.archguard.comate.action.IntroductionPrompt
import org.archguard.comate.strategy.BasicPromptStrategy
import java.io.File
import java.nio.file.Path
import java.nio.file.Paths
import kotlin.io.path.Path
import kotlin.math.sqrt


fun main(args: Array<String>) {
    val basepath = Path(File(".").absolutePath)

    val create = Semantic.create()
    val first = create.embed("analysis system")
    val second = create.embed("analysys systems")

    val similarity = cosineSimilarity(first, second)
    println(similarity)
}

fun cosineSimilarity(first: FloatArray, second: FloatArray): Float {
    var dotProduct = 0f
    var normA = 0f
    var normB = 0f

    for (i in first.indices) {
        dotProduct += first[i] * second[i]
        normA += first[i] * first[i]
        normB += second[i] * second[i]
    }

    val denominator = sqrt(normA) * sqrt(normB)
    return if (denominator == 0f) 0f else dotProduct / denominator
}

class Semantic(val tokenizer: HuggingFaceTokenizer, val session: OrtSession, val env: OrtEnvironment) {
    fun embed(
        sequence: String,
    ): FloatArray {
        val tokenized = tokenizer.encode(sequence, true)

        val inputIds = tokenized.ids
        val attentionMask = tokenized.attentionMask
        val typeIds = tokenized.typeIds

        val tensorInput = OrtUtil.reshape(inputIds, longArrayOf(1, inputIds.size.toLong()))
        val tensorAttentionMask = OrtUtil.reshape(attentionMask, longArrayOf(1, attentionMask.size.toLong()))
        val tensorTypeIds = OrtUtil.reshape(typeIds, longArrayOf(1, typeIds.size.toLong()))

        val result = session.run(
            mapOf(
                "input_ids" to OnnxTensor.createTensor(env, tensorInput),
                "attention_mask" to OnnxTensor.createTensor(env, tensorAttentionMask),
                "token_type_ids" to OnnxTensor.createTensor(env, tensorTypeIds),
            ),
        )

        val outputTensor = result.get(0) as OnnxTensor
        val output = outputTensor.floatBuffer.array()

        return output
    }


    companion object {
        fun create(): Semantic {
            val tokenizer = HuggingFaceTokenizer.newInstance(Paths.get("model/tokenizer.json"))
            val env = OrtEnvironment.getEnvironment()
            val modelPath: String = Path("model/model.onnx").toString()
            val sessionOptions = SessionOptions()
            val session = env.createSession(modelPath, sessionOptions)

            return Semantic(tokenizer, session, env)
        }
    }
}

private fun processCmds(args: Array<String>, basepath: Path): String {
    val cmd = if (args.isEmpty()) {
        "intro"
    } else {
        args[0]
    }

    val command = ComateCommand.valueOf(cmd.capitalize())
    val prompt = command.prompt(basepath)
    return prompt
}

enum class ComateCommand(command: String) {
    Intro("intro") {
        override fun prompt(basepath: Path): String {
            val promptStrategy = BasicPromptStrategy()
            val basicPrompter = IntroductionPrompt(basepath, promptStrategy)
            val prompt = basicPrompter.prompt()
            return prompt
        }
    };

    abstract fun prompt(basepath: Path): String
}