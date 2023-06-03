package org.archguard.comate

import ai.djl.huggingface.tokenizers.HuggingFaceTokenizer
import ai.onnxruntime.OnnxTensor
import ai.onnxruntime.OrtEnvironment
import ai.onnxruntime.OrtSession.SessionOptions
import ai.onnxruntime.OrtUtil
import org.archguard.comate.action.IntroductionPrompt
import org.archguard.comate.strategy.BasicPromptStrategy
import java.io.File
import java.nio.file.Path
import java.nio.file.Paths
import kotlin.io.path.Path


fun main(args: Array<String>) {
    val basepath = Path(File(".").absolutePath)

    val tokenizer = HuggingFaceTokenizer.newInstance(Paths.get("model/tokenizer.json"))
    val sequence = "hello, world";

    embed(tokenizer, sequence)

//    val prompt = processCmds(args, basepath)
//    println(prompt)
}

private fun embed(tokenizer: HuggingFaceTokenizer, sequence: String): FloatArray {
    val tokenized = tokenizer.encode(sequence, true)

    val inputIds = tokenized.ids
    val attentionMask = tokenized.attentionMask
    val typeIds = tokenized.typeIds

    val tensorInput = OrtUtil.reshape(inputIds, longArrayOf(1, inputIds.size.toLong()))
    val tensorAttentionMask = OrtUtil.reshape(attentionMask, longArrayOf(1, attentionMask.size.toLong()))
    val tensorTypeIds = OrtUtil.reshape(typeIds, longArrayOf(1, typeIds.size.toLong()))

    val env = OrtEnvironment.getEnvironment()
    val modelPath: String = Path("model/model.onnx").toString()
    SessionOptions().use { options ->
        env.createSession(modelPath, options).use { session ->
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