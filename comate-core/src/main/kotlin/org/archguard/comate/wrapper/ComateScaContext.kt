package org.archguard.comate.wrapper

import org.archguard.scanner.core.client.ArchGuardClient
import org.archguard.scanner.core.client.EmptyArchGuardClient
import org.archguard.scanner.core.sca.ScaContext

class ComateScaContext(
    override val client: ArchGuardClient,
    override val path: String,
    override val language: String,
) : ScaContext {
    companion object {
        fun create(path: String, language: String): ScaContext {
            return ComateScaContext(EmptyArchGuardClient(), path, language)
        }
    }
}
