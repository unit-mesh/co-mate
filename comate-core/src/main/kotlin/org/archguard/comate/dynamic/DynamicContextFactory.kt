package org.archguard.comate.dynamic

import org.archguard.architecture.style.NamingStyle

interface Function {
    fun define(): String
}

class AnalysisSystem(val url: String) : Function {
    override fun define(): String {
        val pureJavaName = this.javaClass.name.split(".").last()
        val functionName = NamingStyle.toSnakeCase(pureJavaName)
        val constructors = this.javaClass.constructors
        constructors[0].parameters.map {
            println(it.name)
            println(it.type)
        }

//        println(AnalysisSystem::url.name)

        return functionName
    }
}

/**
 * We only accept
 */
class DynamicContextFactory {
    fun functions(): List<String> {
        return this.javaClass.methods
            .filter { it.name.contains("_") }
            .map {
                it.name
            }
    }
}

