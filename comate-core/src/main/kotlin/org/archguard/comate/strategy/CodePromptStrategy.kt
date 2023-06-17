package org.archguard.comate.strategy

import org.archguard.comate.action.base.BaseTemplate

interface CodePromptStrategy : BaseTemplate {
    val strategy: Strategy
    fun execute(): String = strategy.prompt(this)
}