package org.archguard.spec.runtime.compiler

import org.archguard.meta.dsl.RestApiDsl
import org.jetbrains.kotlinx.jupyter.EvalRequestData
import org.jetbrains.kotlinx.jupyter.ReplForJupyter
import org.jetbrains.kotlinx.jupyter.api.Code
import org.jetbrains.kotlinx.jupyter.defaultRepositoriesCoordinates
import org.jetbrains.kotlinx.jupyter.libraries.EmptyResolutionInfoProvider
import org.jetbrains.kotlinx.jupyter.messaging.NoOpDisplayHandler
import org.jetbrains.kotlinx.jupyter.repl.creating.createRepl
import org.slf4j.LoggerFactory
import java.io.File
import kotlin.script.experimental.jvm.util.classPathFromTypicalResourceUrls

class KotlinReplWrapper {
    private val logger = LoggerFactory.getLogger(this.javaClass)
    private val repl: ReplForJupyter

    init {
        this.repl = this.makeEmbeddedRepl()
    }

    private fun makeEmbeddedRepl(): ReplForJupyter {
        val property = System.getProperty("java.class.path")
        var embeddedClasspath: MutableList<File> = property.split(File.pathSeparator).map(::File).toMutableList()

        logger.info("dynamic add classpath for local debug")
        val dslPath = RestApiDsl::javaClass.javaClass.classLoader.classPathFromTypicalResourceUrls().toList()
        embeddedClasspath.addAll(dslPath)

        embeddedClasspath = embeddedClasspath.distinct().toMutableList()

        logger.info("classpath: $embeddedClasspath")

        return createRepl(
            EmptyResolutionInfoProvider,
            embeddedClasspath,
            mavenRepositories = defaultRepositoriesCoordinates,
            libraryResolver = extendLibraries(),
            displayHandler = NoOpDisplayHandler,
            isEmbedded = true
        )
    }

    fun eval(code: Code, jupyterId: Int = -1, storeHistory: Boolean = true) =
        repl.evalEx(EvalRequestData(code, jupyterId, storeHistory))
}
