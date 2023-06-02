package org.archguard.comate.strategy

import org.archguard.comate.action.BaseTemplate

interface Strategy {
    fun prompt(template: BaseTemplate): String
}