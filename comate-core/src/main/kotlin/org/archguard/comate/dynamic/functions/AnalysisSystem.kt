package org.archguard.comate.dynamic.functions

class AnalysisSystem(private val url: String) : Function {
    override fun execute(): Boolean {
        return true
    }

    override fun parameters(): HashMap<String, String> = hashMapOf(
        ::url.name to "String"
    )
}
