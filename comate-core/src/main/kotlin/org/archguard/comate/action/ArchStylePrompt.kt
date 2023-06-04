package org.archguard.comate.action

import org.archguard.comate.strategy.PromptStrategy
import org.archguard.comate.strategy.Strategy
import java.nio.file.Path

class ArchStylePrompt(
    private val workdir: Path,
    override val strategy: Strategy,
) : PromptStrategy {
    override fun getExtendData(): String {

        return ""
    }
}