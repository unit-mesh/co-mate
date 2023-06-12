package org.archguard.meta.dsl

import org.archguard.meta.AtomicAction


class NormalExampleRule : AtomicAction {
    fun pattern(regex: String) {

    }

    fun example(s: String) {

    }

}

class Naming : AtomicAction {

    val should: Should = Should(true)
    val shouldNot: Should = Should(false)

    class Should(val equal: Boolean) : AtomicAction {
        infix fun notEndWith(listOf: List<String>) {

        }
    }
}

class LayeredRule {
    fun pattern(pattern: String): LayeredRule {
        return this
    }

    fun naming(function: Naming.() -> Unit): Naming {
        val rule = Naming()
        rule.function()
        return rule
    }
}

class LayeredDeclaration : AtomicAction {
    fun infrastructure(function: LayeredRule.() -> Unit): LayeredRule {
        val rule = LayeredRule()
        rule.function()
        return rule
    }

}

class BackendSpecDsl {
    fun repository(function: NormalExampleRule.() -> Unit): NormalExampleRule {
        val rule = NormalExampleRule()
        rule.function()
        return rule
    }

    fun layered(function: LayeredDeclaration.() -> Unit): LayeredDeclaration {
        val rule = LayeredDeclaration()
        rule.function()
        return rule
    }

    fun code_style(style: String) {
//    fun class_name_style(style: String) {
//
//    }
//
//    fun method_name_style(style: String) {
//
//    }
//
//    fun variable_name_style(style: String) {
//
//    }
    }

    fun exception(style: String) {

    }

    fun security(style: String) {

    }
}

fun backend(init: BackendSpecDsl.() -> Unit): BackendSpecDsl {
    val html = BackendSpecDsl()
    html.init()
    return html
}
