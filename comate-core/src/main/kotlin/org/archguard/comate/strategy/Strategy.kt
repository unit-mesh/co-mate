package org.archguard.comate.strategy

import org.archguard.comate.action.base.BaseTemplate

interface Strategy {
    fun prompt(template: BaseTemplate): String
}