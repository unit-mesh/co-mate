package org.archguard.comate.strategy

import org.archguard.comate.action.base.BaseTemplate
import org.archguard.comate.command.ComateWorkspace
import org.archguard.comate.document.ReadmeParser
import org.archguard.comate.wrapper.ComateScaContext
import org.archguard.scanner.analyser.*
import org.archguard.scanner.core.sourcecode.LanguageSourceCodeAnalyser
import org.archguard.scanner.core.sourcecode.SourceCodeContext
import java.nio.file.Path
import kotlin.io.path.Path
import kotlin.io.path.exists
import kotlin.io.path.readText

interface CodePromptStrategy : BaseTemplate {
    val strategy: Strategy
    fun execute(): String = strategy.prompt(this)

    fun dependencies(workdir: Path, lang: String) = ScaAnalyser(ComateScaContext.create(workdir.toString(), lang)).analyse()

    fun codeAnalyser(lang: String, context: SourceCodeContext): LanguageSourceCodeAnalyser? = ComateWorkspace.codeAnalyser(lang, context)

    fun introduction(workdir: Path): String = ReadmeParser.introduction(workdir)
}