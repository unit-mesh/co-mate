package org.archguard.comate.action

import org.archguard.comate.command.ComateWorkspace
import org.archguard.comate.strategy.CodePromptStrategy
import org.archguard.comate.strategy.Strategy

class ApiGenPrompter(
    val context: ComateWorkspace,
    override val strategy: Strategy,
) : CodePromptStrategy {

}