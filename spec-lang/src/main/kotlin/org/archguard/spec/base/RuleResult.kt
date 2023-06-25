package org.archguard.spec.base

import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@Serializable
data class RuleResult(val ruleName: String, val rule: String, val success: Boolean, val originValue: String = "") {
    override fun toString(): String {
        return Json.encodeToString(this)
    }
}