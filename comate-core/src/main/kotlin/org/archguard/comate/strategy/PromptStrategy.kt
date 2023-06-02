package org.archguard.comate.strategy

import org.archguard.comate.action.BaseTemplate

interface PromptStrategy: BaseTemplate {
    val strategy: Strategy
    fun prompt(): String = strategy.prompt(this)
}