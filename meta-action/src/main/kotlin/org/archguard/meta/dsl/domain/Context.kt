package org.archguard.meta.dsl.domain

class Aggregate(name: String) {}

class Context(name: String) {
    fun aggregate(name: String, function: Aggregate.() -> Unit): Aggregate {
        val aggregate = Aggregate(name)
        aggregate.function()
        return aggregate
    }

    infix fun dependedOn(targetContext: Context) {
        
    }
}