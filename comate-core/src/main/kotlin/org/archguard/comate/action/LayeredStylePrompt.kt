package org.archguard.comate.action

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.archguard.comate.code.FunctionCall
import org.archguard.comate.strategy.PromptStrategy
import org.archguard.comate.strategy.Strategy
import org.archguard.comate.wrapper.ComateSourceCodeContext
import org.archguard.scanner.analyser.KotlinAnalyser
import java.io.File
import java.nio.file.Path

class LayeredStylePrompt(
    private val workdir: Path,
    val lang: String,
    override val strategy: Strategy,
) : PromptStrategy {
    override fun getExtendData(): String {
        val sourceCodeContext = ComateSourceCodeContext.create(workdir.toString(), lang)
        val codeDataStructs = KotlinAnalyser(sourceCodeContext).analyse()

        val file = File("codedatastructs.json")
        file.writeText(Json.encodeToString(codeDataStructs))

        println(FunctionCall().analysis("org.archguard.comate.cli.MainKt.main", codeDataStructs))

        return ""
    }
}