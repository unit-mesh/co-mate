package org.archguard.comate.dynamic

/**
 * We only accept
 */
class ContextFunction {
    fun analysis_system(url: String): String {
        return ""
    }

    fun introduction_system(url: String): String {
        return ""
    }

    fun functions(): List<String> {
        return this.javaClass.methods
            .filter { it.name.contains("_") }
            .map {
                it.name
            }
    }
}