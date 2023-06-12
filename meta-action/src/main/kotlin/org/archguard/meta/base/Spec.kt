package org.archguard.meta.base

import org.archguard.meta.dsl.restful.RestApi

interface Spec {
    fun context(ruleVerifier: LlmRuleVerifier)
    fun exec(element: RestApi): Map<String, RuleResult>
}