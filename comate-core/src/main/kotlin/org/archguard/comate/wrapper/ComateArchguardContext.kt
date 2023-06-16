package org.archguard.comate.wrapper

import org.archguard.comate.command.ComateWorkspace
import org.archguard.scanner.core.client.ArchGuardClient
import org.archguard.scanner.core.client.EmptyArchGuardClient
import org.archguard.scanner.core.sca.ScaContext
import org.archguard.scanner.core.sourcecode.SourceCodeContext

private val archGuardClient = EmptyArchGuardClient()

class ComateScaContext(
    override val client: ArchGuardClient,
    override val path: String,
    override val language: String,
) : ScaContext {
    companion object {
        fun create(path: String, language: String): ScaContext {
            return ComateScaContext(archGuardClient, path, language)
        }
    }
}

class ComateSourceCodeContext(
    override val client: ArchGuardClient,
    override val features: List<String>,
    override val language: String,
    override val path: String,
) : SourceCodeContext {
    companion object {
        fun create(path: String, language: String, features: List<String> = listOf()): SourceCodeContext {
            return ComateSourceCodeContext(archGuardClient, features, language, path)
        }

        fun create(context: ComateWorkspace): SourceCodeContext {
            return ComateSourceCodeContext(archGuardClient, listOf(), context.language, context.workdir.toString())
        }

        fun custom(client: ArchGuardClient, path: String, language: String, features: List<String> = listOf()): SourceCodeContext {
            return ComateSourceCodeContext(client, features, language, path)
        }
    }
}
