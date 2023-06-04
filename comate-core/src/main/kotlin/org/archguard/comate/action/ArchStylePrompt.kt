package org.archguard.comate.action

import org.archguard.comate.strategy.PromptStrategy
import org.archguard.comate.strategy.Strategy
import org.archguard.comate.wrapper.ComateSourceCodeContext
import java.nio.file.Path

class ArchStylePrompt(
    private val workdir: Path,
    val lang: String,
    override val strategy: Strategy,
) : PromptStrategy {
    override fun getExtendData(): String {
        ComateSourceCodeContext.create(workdir.toString(), lang)
        return ""
    }
}