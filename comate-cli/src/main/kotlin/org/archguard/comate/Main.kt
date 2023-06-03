package org.archguard.comate

import ai.djl.huggingface.tokenizers.HuggingFaceTokenizer
import ai.djl.modality.nlp.bert.BertTokenizer
import ai.djl.modality.nlp.bert.WordpieceTokenizer
import ai.djl.modality.nlp.preprocess.Tokenizer
import org.archguard.comate.action.IntroductionPrompt
import org.archguard.comate.strategy.BasicPromptStrategy
import org.jetbrains.kotlinx.dl.onnx.inference.OnnxInferenceModel
import org.jetbrains.kotlinx.dl.onnx.inference.executionproviders.ExecutionProvider
import org.jetbrains.kotlinx.dl.onnx.inference.inferAndCloseUsing
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
    val tokenTypeIds = tokenized.typeIds
    val length = tokenized.ids.size
//    let inputs_ids_array = ndarray::Array::from_shape_vec(
//            (1, length),
//            input_ids.iter().map(|&x| x as i64).collect(),
//        )?;


//    val model = OnnxInferenceModel("model/model.onnx")
//    model.inferAndCloseUsing(ExecutionProvider.CPU()) {
//        println(it.inputDataType)
//    }
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