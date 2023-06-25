package org.archguard.spec.base

import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

/**
 * We need to find a better way to represent the result of a rule.
 * Since we need to limit tokens in the result, we use a string to represent the result.
 */
@Serializable
data class RuleResult(
    val name: String,
    val rule: String,
    val success: Boolean,
    val value: String = "",
) {
    override fun toString(): String {
        return Json.encodeToString(this)
    }
}