package org.archguard.spec.base

interface Rule<T> {
    val actionName: String
    fun exec(input: T): Any {
        return false
    }
}
