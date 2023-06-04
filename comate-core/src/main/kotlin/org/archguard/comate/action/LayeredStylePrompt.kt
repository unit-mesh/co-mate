package org.archguard.comate.action

import org.archguard.comate.strategy.PromptStrategy
import org.archguard.comate.strategy.Strategy
import org.archguard.comate.wrapper.ComateSourceCodeContext
import org.archguard.scanner.analyser.KotlinAnalyser
import java.nio.file.Path

class LayeredStylePrompt(
    private val workdir: Path,
    val lang: String,
    override val strategy: Strategy,
) : PromptStrategy {
    override fun getExtendData(): String {
        val sourceCodeContext = ComateSourceCodeContext.create(workdir.toString(), lang)
        val codeDataStructs = KotlinAnalyser(sourceCodeContext).analyse()

        println(codeDataStructs)

        // filter Main.kt
        val codeDataStructsFiltered = codeDataStructs.filter { it.FilePath.endsWith("Main.kt") }

        return ""
    }
}