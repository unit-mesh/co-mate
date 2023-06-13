package org.archguard.meta.dsl.domain

class Context(name: String) {
    fun aggregate(name: String, function: () -> Unit) {

    }

    infix fun dependedOn(context: Context) {

    }

}