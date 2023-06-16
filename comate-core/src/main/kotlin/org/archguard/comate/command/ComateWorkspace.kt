package org.archguard.comate.command

import org.archguard.comate.connector.OpenAIConnector
import java.nio.file.Path

data class ComateWorkspace(
    val workdir: Path,
    val language: String,
    val connector: OpenAIConnector?,
    val extArgs: Map<String, String> = emptyMap(),
)