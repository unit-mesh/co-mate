package org.archguard.meta


@DslMarker
annotation class AtomicDsl

interface Action {
    fun exec(input: Element): Any
}

open class Element

@AtomicDsl
abstract class ApiRule(val name: String) : Action {
    abstract override fun exec(input: Element): Any
}

class RestApi: Element() {

}