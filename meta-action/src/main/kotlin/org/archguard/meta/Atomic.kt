package org.archguard.meta


@DslMarker
annotation class AtomicDsl

interface AtomicAction {

}

open class Element

@AtomicDsl
abstract class ApiRule(val name: String) : AtomicAction {
    abstract fun exec(input: RestApi): Any
}

class RestApi(val uri: String) : Element() {

}