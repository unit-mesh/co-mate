package org.archguard.meta

import kotlinx.serialization.json.JsonObject


@DslMarker
annotation class AtomicDsl

interface AtomicAction {

}

open class Element

@AtomicDsl
abstract class ApiRule(val name: String) : AtomicAction {
    abstract fun exec(input: RestApi): Any
}

data class RestApi(
    val uri: String,
    val action: String,
    val statusCodes: List<Int>,
    val request: JsonObject? = null,
    val response: List<JsonObject> = listOf(),
) : Element() {

}