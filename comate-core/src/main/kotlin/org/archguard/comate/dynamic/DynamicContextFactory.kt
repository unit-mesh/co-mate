package org.archguard.comate.dynamic

import org.archguard.comate.dynamic.functions.ComateFunction
import org.archguard.comate.dynamic.functions.DyFunction
import org.reflections.Reflections

// Assuming you want to scan classes in the current package
fun findClasses(): List<Class<out DyFunction>> {
    val packageName = DyFunction::class.java.`package`.name
    val reflections = Reflections(packageName)

    val annotated: Set<Class<out DyFunction>> =
        reflections.getTypesAnnotatedWith(ComateFunction::class.java) as Set<Class<out DyFunction>>

    return annotated.toList()
}


class DynamicContextFactory {
    fun functions(): List<String> {
        return findClasses().map {
            it.newInstance().define()
        }
    }
}
