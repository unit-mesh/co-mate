package org.archguard.comate.dynamic

import org.archguard.comate.command.ComateContext
import org.archguard.comate.dynamic.functions.ComateFunction
import org.archguard.comate.dynamic.functions.DyFunction
import org.reflections.Reflections
import kotlin.reflect.full.primaryConstructor

// Assuming you want to scan classes in the current package
fun findClasses(): List<Class<out DyFunction>> {
    val packageName = DyFunction::class.java.`package`.name
    val reflections = Reflections(packageName)

    val annotated: Set<Class<out DyFunction>> =
        reflections.getTypesAnnotatedWith(ComateFunction::class.java) as Set<Class<out DyFunction>>

    return annotated.toList()
}


class DynamicContextFactory(val context: ComateContext) {
//    private var classes: List<Class<out DyFunction>>
//
//    init {
//        this.classes = findClasses()
//    }

    fun functions(): List<String> {
        return findClasses().map { clazz ->
            val dyFunction = clazz::class.primaryConstructor?.call(context)
            dyFunction?.newInstance()?.define() ?: ""
        }
    }

//    fun runByFunctionName(functionName: String): Boolean {
//        val clazz = findClasses().find { clazz ->
//            val dyFunction = clazz::class.primaryConstructor?.call(context)
//            dyFunction?.newInstance()?.define() == functionName
//        }
//
//        val dyFunction = clazz?.primaryConstructor?.call(context)
//        return dyFunction?.newInstance()?.execute() ?: false
//    }
}
