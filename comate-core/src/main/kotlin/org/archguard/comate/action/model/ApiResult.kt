package org.archguard.comate.action.model

import kotlinx.serialization.Serializable
import org.archguard.spec.base.RuleResult

@Serializable
data class ApiResult(val apiUri: String, val result: List<RuleResult>)