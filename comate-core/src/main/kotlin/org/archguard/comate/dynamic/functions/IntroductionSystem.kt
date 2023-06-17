package org.archguard.comate.dynamic.functions

@ComateFunction
class IntroductionSystem(private var url: String) : DyFunction {
    // TODO: Add a constructor with no parameters
    constructor() : this("http://localhost:8080") {
    }

    override fun execute(): Boolean {
        return true
    }

    override fun parameters(): HashMap<String, String> = hashMapOf(
        ::url.name to "String"
    )
}
