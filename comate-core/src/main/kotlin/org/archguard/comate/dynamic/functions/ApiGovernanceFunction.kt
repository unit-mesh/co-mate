package org.archguard.comate.dynamic.functions

import org.archguard.comate.command.ComateContext

@ComateFunction
class ApiGovernanceFunction(override val context: ComateContext) : DyFunction {
    override fun explain(): String {
        return "REST API Governance function, based on API Specification."
    }

    override fun execute(): FunctionResult.Success<Boolean> {
        return FunctionResult.Success(true)
    }

    override fun parameters(): HashMap<String, String> = hashMapOf(

    )
}
