package org.archguard.meta.dsl

import org.archguard.meta.AtomicAction


class NormalExampleRule : AtomicAction {
    fun regex(s: String) {
    }

    fun example(s: String) {

    }

}

class BackendSpecDsl {
    fun repository(function: NormalExampleRule.() -> Unit): NormalExampleRule {
        val rule = NormalExampleRule()
        rule.function()
        return rule
    }

    fun layered_style(style: String) {

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
