package org.archguard.spec.lang.concept

import kotlinx.serialization.Serializable

@Serializable
data class Behavior(val action: String, val description: String = "")