package org.archguard.comate.action

import org.archguard.comate.code.FunctionCall
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

    override fun getRole(): String = "Architecture"
    override fun getInstruction(): String = "根据如下的信息，分析这部分的业务场景，并使用 200 个字介绍。"

    override fun getExtendData(): String {
        val sourceCodeContext = ComateSourceCodeContext.create(workdir.toString(), lang)
        val codeDataStructs = KotlinAnalyser(sourceCodeContext).analyse()

        val nodeTree = FunctionCall().analysis("org.archguard.comate.cli.MainKt.main", codeDataStructs)

        return nodeTree.toString()
    }
}