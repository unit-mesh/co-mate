package org.archguard.comate.strategy

import org.archguard.comate.action.BaseTemplate
import org.archguard.comate.document.ReadmeParser
import java.nio.file.Path
import kotlin.io.path.Path
import kotlin.io.path.exists
import kotlin.io.path.readText

interface PromptStrategy : BaseTemplate {
    val strategy: Strategy
    fun prompt(): String = strategy.prompt(this)

    fun introduction(workdir: Path): String {
        var instr = "";
        val readmeFile = Path(workdir.toString(), "README.md")
        if (readmeFile.exists()) {
            val readme = readmeFile.readText()
            val readmeParser = ReadmeParser(readme)
            val introduction = readmeParser.introduction()
            instr = "\nProject Instruction: ${introduction.content}\n"
        }

        return instr
    }
}