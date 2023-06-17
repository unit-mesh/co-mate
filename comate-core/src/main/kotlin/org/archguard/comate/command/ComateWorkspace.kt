package org.archguard.comate.command

import chapi.domain.core.CodeDataStruct
import org.archguard.comate.connector.OpenAIConnector
import org.archguard.comate.wrapper.ComateScaContext
import org.archguard.comate.wrapper.ComateSourceCodeContext
import org.archguard.scanner.analyser.*
import org.archguard.scanner.core.sca.CompositionDependency
import org.archguard.scanner.core.sourcecode.LanguageSourceCodeAnalyser
import org.archguard.scanner.core.sourcecode.SourceCodeContext
import java.nio.file.Path

data class ComateWorkspace(
    val workdir: Path,
    val language: String,
    val connector: OpenAIConnector?,
    val extArgs: Map<String, String> = emptyMap(),
    private val ds: List<CodeDataStruct> = emptyList(),
    private val dependencies: List<CompositionDependency> = emptyList(),
) {

    fun getDs(forceScan: Boolean = false): List<CodeDataStruct> {
        if (ds.isNotEmpty() && !forceScan) {
            return ds
        }

        val codeContext = ComateSourceCodeContext.create(this)
        val codeDataStructs = Companion.codeAnalyser(language, codeContext)?.analyse()

        return codeDataStructs ?: emptyList()
    }

    fun getDependencies(forceScan: Boolean = false): List<CompositionDependency> {
        if (dependencies.isNotEmpty() && !forceScan) {
            return dependencies
        }

        return ScaAnalyser(ComateScaContext.create(workdir.toString(), this.language)).analyse()
    }

    companion object {
        fun codeAnalyser(lang: String, context: SourceCodeContext): LanguageSourceCodeAnalyser? {
            return when (lang) {
                "java" -> {
                    JavaAnalyser(context)
                }

                "kotlin" -> {
                    KotlinAnalyser(context)
                }

                "javascript", "typescript" -> {
                    TypeScriptAnalyser(context)
                }

                "golang" -> {
                    GoAnalyser(context)
                }

                "python" -> {
                    PythonAnalyser(context)
                }

                else -> {
                    null
                }
            }
        }
    }
}