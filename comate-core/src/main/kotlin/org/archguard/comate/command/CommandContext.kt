package org.archguard.comate.command

import org.archguard.comate.smart.OpenAIConnector
import java.nio.file.Path

data class CommandContext(
    val workdir: Path,
    val lang: String,
    val connector: OpenAIConnector?,
    val extArgs: Map<String, String> = emptyMap(),
)