package org.archguard.meta


@DslMarker
annotation class AtomicDsl

interface AtomicAction {
    fun exec(input: Element): Any
}

open class Element

@AtomicDsl
abstract class ApiRule(val name: String) : AtomicAction {
    abstract override fun exec(input: Element): Any
}

class RestApi(uri: String) : Element() {

}