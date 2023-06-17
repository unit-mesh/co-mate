package org.archguard.comate.dynamic.functions

@ComateFunction
class IntroductionSystem(private val url: String) : DyFunction {
    override fun execute(): Boolean {
        return true
    }

    override fun parameters(): HashMap<String, String> = hashMapOf(
        ::url.name to "String"
    )
}
