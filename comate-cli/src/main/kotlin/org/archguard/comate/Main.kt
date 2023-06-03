package org.archguard.comate

import ai.djl.huggingface.tokenizers.HuggingFaceTokenizer
import ai.djl.ndarray.NDManager
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

    val manager = NDManager.newBaseManager()

    val inputIdsArray = manager.create(tokenized.ids)
    val attentionMaskArray = manager.create(tokenized.attentionMask)
    val tokenTypeIdsArray = manager.create(tokenized.typeIds)

//        let outputs = self.session.run([
//            InputTensor::from_array(inputs_ids_array.into_dyn()),
//            InputTensor::from_array(attention_mask_array.into_dyn()),
//            InputTensor::from_array(token_type_ids_array.into_dyn()),
//        ])?;

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