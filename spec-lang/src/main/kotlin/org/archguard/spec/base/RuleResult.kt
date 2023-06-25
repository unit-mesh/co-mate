package org.archguard.spec.base

import kotlinx.serialization.Serializable

@Serializable
data class RuleResult(val ruleName: String, val rule: String, val success: Boolean)