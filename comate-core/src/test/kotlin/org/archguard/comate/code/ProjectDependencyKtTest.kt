package org.archguard.comate.code

import org.archguard.scanner.core.sca.CompositionDependency
import org.junit.jupiter.api.Test
import kotlin.io.path.Path

class ProjectDependencyKtTest {
    @Test
    fun should_return_correct() {
        val dep: List<CompositionDependency> = listOf()
        val result = CompositionDependency.dependencyMapping(dep, Path("."))

        assert(result.isEmpty())
    }
}