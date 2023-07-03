package org.archguard.comate.server.action

import org.archguard.comate.command.ComateContext
import org.archguard.comate.dynamic.functions.*

enum class ComateToolingAction(val action: String) {
    INTRODUCE_SYSTEM(action = "introduce_system") {
        override fun execute(comateContext: ComateContext): FunctionResult.Success<String> {
            InitializeSystemFunction(comateContext).execute()
            return IntroduceSystemFunction(comateContext).execute()
        }
    },
    REST_API_GOVERNANCE(action = "rest_api_governance") {
        override fun execute(comateContext: ComateContext): FunctionResult.Success<String> {
            return RestApiGovernanceFunction(comateContext).execute()
        }
    },
    FOUNDATION_SPEC_GOVERNANCE(action = "foundation_spec_governance") {
        override fun execute(comateContext: ComateContext): FunctionResult.Success<String> {
            return FoundationSpecGovernanceFunction(comateContext).execute()
        }
    },
    DESIGN_SYSTEM_WITH_REQUIREMENT(action = "design_system_with_requirement") {
        override fun execute(comateContext: ComateContext): FunctionResult.Success<String> {
            return DesignSystemWithRequirementFunction(comateContext).execute()
        }
    },
    ;

    abstract fun execute(comateContext: ComateContext): FunctionResult.Success<Any>

    companion object {
        fun from(action: String): ComateToolingAction? {
            return values().find { it.action == action.lowercase() }
        }
    }
}