package org.archguard.comate.action

import org.archguard.comate.strategy.CodePromptStrategy
import org.archguard.comate.strategy.Strategy

class ApiGenPrompter(
    val context: CommandContext,
    override val strategy: Strategy,
) : CodePromptStrategy {

}