package org.archguard.comate.dynamic.functions

import org.archguard.comate.command.ComateContext
import org.archguard.comate.command.fakeComateContext

@ComateFunction
class InitializeSystem(override val context: ComateContext = fakeComateContext()) : DyFunction {
    override fun explain(): String {
        return "Initialize system will clone the repository and setup it."
    }

    override fun execute(): Boolean {
        TODO("Not yet implemented")
    }

    override fun parameters(): HashMap<String, String> {
        return hashMapOf()
    }
}