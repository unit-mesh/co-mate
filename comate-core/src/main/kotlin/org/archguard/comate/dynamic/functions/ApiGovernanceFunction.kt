package org.archguard.comate.dynamic.functions

@ComateFunction
class ApiGovernanceFunction(private var url: String) : DyFunction {
    constructor() : this("https://github.com/archguard/co-mate") {
    }

    override fun explain(): String {
        return "REST API Governance function, based on API Specification."
    }

    override fun execute(): Boolean {
        return true
    }

    override fun parameters(): HashMap<String, String> = hashMapOf(
        ::url.name to "String"
    )
}