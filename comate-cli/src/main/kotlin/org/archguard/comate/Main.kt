package org.archguard.comate

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
import kotlin.io.path.Path

fun main(args: Array<String>) {
    val basepath = Path(File(".").absolutePath)

    val model = OnnxInferenceModel("model/model.onnx")

    model.inferAndCloseUsing(ExecutionProvider.CPU()) {
        println(it.inputDataType)
    }
//    val prompt = processCmds(args, basepath)
//    println(prompt)
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