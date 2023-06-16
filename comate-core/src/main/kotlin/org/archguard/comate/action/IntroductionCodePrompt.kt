package org.archguard.comate.action

import org.archguard.scanner.core.sca.CompositionDependency
import java.nio.file.Path
import kotlin.io.path.Path
import kotlin.io.path.relativeTo

fun CompositionDependency.Companion.dependencyMapping(
    dep: List<CompositionDependency>,
    workdir: Path,
): Map<String, List<String>> {
    return dep.groupBy {
        val relativePath = Path(it.path).relativeTo(workdir).toString()
        relativePath
    }.mapValues { entry ->
        entry.value.map { it.depName }
            .toSet()
            .filter { it.isNotEmpty() && it != ":" }
    }
}
