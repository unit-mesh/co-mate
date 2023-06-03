package org.archguard.comate

import ai.djl.huggingface.tokenizers.HuggingFaceTokenizer
import ai.onnxruntime.OnnxTensor
import ai.onnxruntime.OrtEnvironment
import ai.onnxruntime.OrtSession.SessionOptions
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

//            let tokenizer_output = self.tokenizer.encode(sequence, true).unwrap();
//
//        let input_ids = tokenizer_output.get_ids();
//        let attention_mask = tokenizer_output.get_attention_mask();
//        let token_type_ids = tokenizer_output.get_type_ids();

    embed(tokenizer, sequence)

//    val prompt = processCmds(args, basepath)
//    println(prompt)
}

private fun embed(tokenizer: HuggingFaceTokenizer, sequence: String) {
    val tokenized = tokenizer.encode(sequence, true)

    val inputIds = tokenized.ids
    val attentionMask = tokenized.attentionMask
    val typeIds = tokenized.typeIds

//    val manager = NDManager.newBaseManager()
//    val inputIdsArray = manager.create(inputIds)
//    val attentionMaskArray = manager.create(attentionMask)
//    val tokenTypeIdsArray = manager.create(typeIds)

//        let outputs = self.session.run([
//            InputTensor::from_array(inputs_ids_array.into_dyn()),
//            InputTensor::from_array(attention_mask_array.into_dyn()),
//            InputTensor::from_array(token_type_ids_array.into_dyn()),
//        ])?;

//    val model = OnnxInferenceModel("model/model.onnx")
//    val detections = model.inferAndCloseUsing(ExecutionProvider.CPU()) {
//    }


    val env = OrtEnvironment.getEnvironment()
    val modelPath: String = Path("model/model.onnx").toString()
    SessionOptions().use { options ->
//        val a = OnnxTensor.createTensor(env, floatArrayOf(2.0f))
        env.createSession(modelPath, options).use { session ->
            val output = session.run(
                mapOf(
                    "input_ids" to OnnxTensor.createTensor(env, inputIds),
                    "attention_mask" to OnnxTensor.createTensor(env, attentionMask),
                    "token_type_ids" to OnnxTensor.createTensor(env, typeIds),
                ),
            )
            println(output[0])
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