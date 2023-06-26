package org.archguard.comate.server.action

import org.archguard.comate.dynamic.functions.*

enum class ToolingAction(val action: String) {
    INTRODUCE_SYSTEM(action = "introduce_system") {
        override fun execute(input: String): FunctionResult.Success<String> {
            comateContext.projectRepo = input
            InitializeSystemFunction(comateContext).execute()
            return IntroduceSystemFunction(comateContext).execute()
        }
    },
    REST_API_GOVERNANCE(action = "rest_api_governance") {
        override fun execute(input: String): FunctionResult.Success<String> {
            return RestApiGovernanceFunction(comateContext).execute()
        }
    },
    FOUNDATION_SPEC_GOVERNANCE(action = "foundation_spec_governance") {
        override fun execute(input: String): FunctionResult.Success<String> {
            return FoundationSpecGovernanceFunction(comateContext).execute()
        }
    },
    ;

    abstract fun execute(input: String): FunctionResult.Success<Any>
}