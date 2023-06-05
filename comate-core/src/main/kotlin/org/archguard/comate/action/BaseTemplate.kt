package org.archguard.comate.action

import org.archguard.scanner.core.sca.CompositionDependency
import java.nio.file.Path

interface BaseTemplate {
    fun getRole(): String = ""
    fun getInstruction(): String = ""
    fun getRequirements(): String = ""
    fun getSample(): String = ""
    fun getExtendData(): String = ""
    fun dependencies(workdir: Path, lang: String): List<CompositionDependency>
}
