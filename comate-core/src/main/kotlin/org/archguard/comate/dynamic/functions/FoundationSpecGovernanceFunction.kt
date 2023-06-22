package org.archguard.comate.dynamic.functions

import org.archguard.comate.action.ApiGovernancePrompter
import org.archguard.comate.action.FoundationGovernancePrompter
import org.archguard.comate.command.ComateContext

@ComateFunction
class FoundationSpecGovernanceFunction(override val context: ComateContext) : DyFunction {
    override fun explain(): String {
        return "REST API Governance function, based on API Specification."
    }

    override fun execute(): FunctionResult.Success<String> {
        val output = FoundationGovernancePrompter(context, context.strategy).execute()
        return FunctionResult.Success(output)
    }

    override fun parameters(): HashMap<String, String> = hashMapOf(

    )
}
