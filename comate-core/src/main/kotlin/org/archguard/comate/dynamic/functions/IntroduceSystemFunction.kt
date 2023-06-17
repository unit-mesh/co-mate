package org.archguard.comate.dynamic.functions

@ComateFunction
class IntroduceSystemFunction(private var url: String) : DyFunction {
    // TODO: Add a constructor with no parameters
    constructor() : this("https://github.com/archguard/co-mate") {

    }

    override fun explain(): String {
        return "Introduce system based on README.md, project dependencies."
    }

    override fun execute(): Boolean {
        return true
    }

    override fun parameters(): HashMap<String, String> = hashMapOf(
        ::url.name to "String"
    )
}
