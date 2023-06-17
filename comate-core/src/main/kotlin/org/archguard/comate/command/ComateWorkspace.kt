package org.archguard.comate.command

import chapi.domain.core.CodeDataStruct
import org.archguard.comate.code.ServicesMap
import org.archguard.comate.connector.OpenAIConnector
import org.archguard.comate.document.ReadmeParser
import org.archguard.comate.wrapper.ComateScaContext
import org.archguard.comate.wrapper.ComateSourceCodeContext
import org.archguard.scanner.analyser.*
import org.archguard.scanner.core.sca.CompositionDependency
import org.archguard.scanner.core.sourcecode.LanguageSourceCodeAnalyser
import org.archguard.scanner.core.sourcecode.SourceCodeContext
import org.archguard.spec.element.RestApiElement
import java.nio.file.Path

data class ComateWorkspace(
    val workdir: Path,
    val language: String,
    val connector: OpenAIConnector?,
    val extArgs: Map<String, String> = emptyMap(),
    private val ds: List<CodeDataStruct> = emptyList(),
    private val dependencies: List<CompositionDependency> = emptyList(),
    private val apis: List<RestApiElement> = emptyList(),
) {
    val codeContext = ComateSourceCodeContext.create(this)

    fun getDs(forceScan: Boolean = false): List<CodeDataStruct> {
        if (ds.isNotEmpty() && !forceScan) {
            return ds
        }

        val codeDataStructs = Companion.codeAnalyser(language, codeContext)?.analyse()

        return codeDataStructs ?: emptyList()
    }

    fun getDependencies(forceScan: Boolean = false): List<CompositionDependency> {
        if (dependencies.isNotEmpty() && !forceScan) {
            return dependencies
        }

        return ScaAnalyser(ComateScaContext.create(workdir.toString(), this.language)).analyse()
    }

    fun getApis(forceScan: Boolean = false): List<RestApiElement> {
        if (apis.isNotEmpty() && !forceScan) {
            return apis
        }

        val codeDataStructs = this.getDs()
        return ServicesMap.scanApis(codeDataStructs, codeContext)
    }

    fun getReadmeIntroduction(): String {
        return ReadmeParser.introduction(workdir)
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