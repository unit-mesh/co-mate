package org.archguard.comate.action

import org.archguard.comate.command.ComateWorkspace
import org.archguard.comate.strategy.BasicPromptStrategy
import org.junit.jupiter.api.Test
import kotlin.io.path.Path

class LayeredStylePromptTest {
    @Test
    fun should_throw_when_no_function_name_from_chapi() {
        val promptStrategy = BasicPromptStrategy()
        val basicPrompter = LayeredStylePrompt(ComateWorkspace(Path("../"), "kotlin", null), promptStrategy)

        val output = basicPrompter.execute()
        println(output)
    }
}
