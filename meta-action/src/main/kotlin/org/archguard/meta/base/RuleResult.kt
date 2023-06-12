package org.archguard.meta.base

import kotlinx.serialization.Serializable

@Serializable
data class RuleResult(val ruleName: String, val rule: String, val result: Boolean)