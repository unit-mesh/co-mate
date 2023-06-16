package org.archguard.comate.command

import chapi.domain.core.CodeDataStruct
import org.archguard.comate.connector.OpenAIConnector
import java.nio.file.Path

data class ComateWorkspace(
    val workdir: Path,
    val language: String,
    val connector: OpenAIConnector?,
    val ds: List<CodeDataStruct> = emptyList(),
    val extArgs: Map<String, String> = emptyMap(),
)