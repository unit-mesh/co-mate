package org.archguard.comate.strategy

import org.archguard.comate.action.BaseTemplate
import org.archguard.comate.document.ReadmeParser
import org.archguard.comate.wrapper.ComateScaContext
import org.archguard.scanner.analyser.ScaAnalyser
import java.nio.file.Path
import kotlin.io.path.Path
import kotlin.io.path.exists
import kotlin.io.path.readText

interface CodePromptStrategy : BaseTemplate {
    val strategy: Strategy
    fun prompt(): String = strategy.prompt(this)

    override fun dependencies(workdir: Path, lang: String) = ScaAnalyser(ComateScaContext.create(workdir.toString(), lang)).analyse()

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