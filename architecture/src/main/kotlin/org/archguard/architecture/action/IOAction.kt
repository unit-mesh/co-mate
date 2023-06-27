package org.archguard.architecture.action

interface IOAction {
    fun execute()
}

class EmptyIOAction(): IOAction {
    override fun execute() {

    }
}