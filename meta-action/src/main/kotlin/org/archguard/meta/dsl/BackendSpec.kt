package org.archguard.meta.dsl

import org.archguard.meta.dsl.foundation.LayeredDeclaration
import org.archguard.meta.dsl.foundation.NamingDeclaration
import org.archguard.meta.dsl.foundation.ProjectNameDecl

class DependencyRule {
    val rules = mutableListOf<Pair<String, String>>()
    infix fun String.dependedOn(to: String) {
        rules.add(this to to)
    }
}

class BackendSpec {
    fun project_name(function: ProjectNameDecl.() -> Unit): ProjectNameDecl {
        val rule = ProjectNameDecl()
        rule.function()
        return rule
    }

    fun layered(function: LayeredDeclaration.() -> Unit): LayeredDeclaration {
        val rule = LayeredDeclaration()
        rule.function()
        return rule
    }


    fun naming(function: NamingDeclaration.() -> Unit): NamingDeclaration {
        val rule = NamingDeclaration()
        rule.function()
        return rule
    }
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

    fun exception(style: String) {

    }

    fun security(style: String) {

    }
}

fun backend(init: BackendSpec.() -> Unit): BackendSpec {
    val html = BackendSpec()
    html.init()
    return html
}
