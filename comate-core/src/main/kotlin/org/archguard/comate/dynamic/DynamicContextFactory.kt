package org.archguard.comate.dynamic

class DynamicContextFactory {
    fun functions(): List<String> {
        return this.javaClass.methods
            .filter { it.name.contains("_") }
            .map {
                it.name
            }
    }
}

