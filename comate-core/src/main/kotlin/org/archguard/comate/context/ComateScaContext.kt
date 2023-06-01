package org.archguard.comate.context

import org.archguard.comate.ComateArchGuardClient
import org.archguard.scanner.core.client.ArchGuardClient
import org.archguard.scanner.core.sca.ScaContext

class ComateScaContext(
    override val client: ArchGuardClient,
    override val path: String,
    override val language: String,
) : ScaContext {
    companion object {
        fun create(path: String, language: String): ScaContext {
            val client = ComateArchGuardClient()
            return ComateScaContext(client, path, language)
        }
    }
}
