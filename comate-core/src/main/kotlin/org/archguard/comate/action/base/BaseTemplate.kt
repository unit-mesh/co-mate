package org.archguard.comate.action.base

interface BaseTemplate {
    fun getRole(): String = ""
    fun getInstruction(): String = ""
    fun getRequirements(): String = ""
    fun getSample(): String = ""
    fun getExtendData(): String = ""
}
