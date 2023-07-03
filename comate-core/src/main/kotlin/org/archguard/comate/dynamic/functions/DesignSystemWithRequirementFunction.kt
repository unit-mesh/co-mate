package org.archguard.comate.dynamic.functions

import org.archguard.comate.action.DesignSystemPrompter
import org.archguard.comate.command.ComateContext

@ComateFunction
class DesignSystemWithRequirementFunction(override val context: ComateContext) : DyFunction {
    override fun explain(): String {
        return "Design system architecture with requirement"
    }

    override fun execute(): FunctionResult.Success<String> {
        val output = DesignSystemPrompter(context, context.strategy).execute()
        return FunctionResult.Success(output)
    }

    override fun parameters(): HashMap<String, String> = hashMapOf(

    )
}
