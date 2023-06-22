package org.archguard.comate.command

import chapi.domain.core.CodeDataStruct
import org.archguard.comate.code.ServicesMap
import org.archguard.comate.code.generatePackageDependencies
import org.archguard.comate.connector.OpenAIConnector
import org.archguard.comate.document.ReadmeParser
import org.archguard.comate.strategy.BasicPromptStrategy
import org.archguard.comate.strategy.Strategy
import org.archguard.comate.wrapper.ComateScaContext
import org.archguard.comate.wrapper.ComateSourceCodeContext
import org.archguard.scanner.analyser.*
import org.archguard.scanner.core.sca.CompositionDependency
import org.archguard.scanner.core.sourcecode.LanguageSourceCodeAnalyser
import org.archguard.scanner.core.sourcecode.SourceCodeContext
import org.archguard.spec.element.RestApiElement
import java.nio.file.Path
import kotlin.io.path.Path

data class ComateContext(
    var workdir: Path,
    val language: String,
    var connector: OpenAIConnector?,
    var projectRepo: String = "",
    val extArgs: Map<String, String> = emptyMap(),
    val strategy: Strategy = BasicPromptStrategy(),
    private val ds: List<CodeDataStruct> = emptyList(),
    private val projectDependencies: List<CompositionDependency> = emptyList(),
    private val apis: List<RestApiElement> = emptyList(),
) {
    private val codeContext = ComateSourceCodeContext.create(this)

    val projectName = workdir.fileName.toString()

    fun fetchDs(forceScan: Boolean = false): List<CodeDataStruct> {
        if (ds.isNotEmpty() && !forceScan) {
            return ds
        }

        val codeDataStructs = codeAnalyser(language, codeContext)?.analyse()
        return codeDataStructs ?: emptyList()
    }

    fun fetchProjectDependencies(forceScan: Boolean = false): List<CompositionDependency> {
        if (projectDependencies.isNotEmpty() && !forceScan) {
            return projectDependencies
        }

        val scaContext = ComateScaContext.create(workdir.toString(), this.language)
        return ScaAnalyser(scaContext).analyse()
    }

    fun fetchPackageDependencies(forceScan: Boolean = false): Map<String, List<String>> {
        val codeDataStructs = this.fetchDs()
        return CodeDataStruct.generatePackageDependencies(codeDataStructs)
    }

    fun fetchApis(forceScan: Boolean = false): List<RestApiElement> {
        if (apis.isNotEmpty() && !forceScan) {
            return apis
        }

        val codeDataStructs = this.fetchDs()
        return ServicesMap.scanApis(codeDataStructs, codeContext)
    }

    fun fetchReadmeIntroduction(): String {
        return ReadmeParser.introduction(workdir)
    }

    companion object {
        fun codeAnalyser(lang: String, context: SourceCodeContext): LanguageSourceCodeAnalyser? {
            return when (lang.lowercase()) {
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

fun fakeComateContext(): ComateContext {
    return ComateContext(Path(path = "."), "java", null)
}
