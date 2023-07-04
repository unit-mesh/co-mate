package org.archguard.spec.lang

import kotlinx.serialization.Serializable
import org.archguard.spec.lang.base.Spec

@Serializable
data class Class(
    val packageName: String,
    val name: String,
    val properties: MutableList<Property> = mutableListOf(),
    val methods: MutableList<Method> = mutableListOf(),
)

@Serializable
data class Property(val name: String, val type: String)

@Serializable
data class Method(val name: String, val returnType: String, val parameters: List<Parameter> = emptyList())

@Serializable
data class Parameter(val name: String, val type: String)

class ClassSpec(private val className: String, private val packageName: String) {
    private val properties = mutableListOf<Property>()
    private val methods = mutableListOf<Method>()

    fun prop(name: String, type: String) {
        properties.add(Property(name, type))
    }

    fun method(name: String, returnType: String, block: MethodBuilder.() -> Unit) {
        val methodBuilder = MethodBuilder(name, returnType)
        methodBuilder.block()
        methods.add(methodBuilder.build())
    }

    fun build(): Class {
        return Class(packageName, className, properties, methods)
    }

    fun prop(name: Pair<String, String>) {
        properties.add(Property(name.first, name.second))
    }

    inner class MethodBuilder(private val name: String, private val returnType: String) {
        private val parameters = mutableListOf<Parameter>()

        fun parameter(name: String, type: String) {
            parameters.add(Parameter(name, type))
        }

        fun build(): Method {
            return Method(name, returnType, parameters)
        }
    }
}

class ConceptSpec : Spec<String> {
    override fun default(): Spec<String> {
        return defaultSpec()
    }

    override fun toString(): String {
        return "ConceptSpec()"
    }

    companion object {
        fun defaultSpec(): ConceptSpec {
            return concepts {
                concept("Human") {

                }
                concept("Person", "com.biz.domain") {
                    prop("name" to "String")
                    prop("age", "Int")

                    method("speak", "Unit") {
                        parameter("message", "String")
                    }
                }
            }
        }
    }

    fun concept(className: String, packageName: String = "", function: ClassSpec.() -> Unit): ClassSpec {
        val classSpec = ClassSpec(className, packageName)
        classSpec.function()
        return classSpec
    }
}

/**
 * Concept DSL is a way to describe the concept(object) and their connections of a software system.
 */
fun concepts(function: ConceptSpec.() -> Unit): ConceptSpec {
    val conceptSpec = ConceptSpec()
    conceptSpec.function()
    return conceptSpec
}
