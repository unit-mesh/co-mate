package org.archguard.comate.dynamic

import org.archguard.comate.command.ComateContext
import org.archguard.comate.dynamic.functions.ComateFunction
import org.archguard.comate.dynamic.functions.DyFunction
import org.archguard.comate.dynamic.functions.toSnakeCase
import org.reflections.Reflections

// Assuming you want to scan classes in the current package
fun findClasses(): List<Class<out DyFunction>> {
    val packageName = DyFunction::class.java.`package`.name
    val reflections = Reflections(packageName)

    val annotated: Set<Class<out DyFunction>> =
        reflections.getTypesAnnotatedWith(ComateFunction::class.java) as Set<Class<out DyFunction>>

    return annotated.toList()
}


class DynamicContextFactory(val context: ComateContext) {
    private var classMap: Map<String, DyFunction> = mapOf()

    init {
        classMap = findClasses().associate { clazz ->
            val defaultConstructor = clazz.declaredConstructors[0]
            val dyFunction = defaultConstructor.newInstance(context) as DyFunction
            clazz.name.toSnakeCase() to dyFunction
        }
    }

    fun functions(): List<String> {
        return this.classMap.map {
            it.value.define()
        }
    }
}
