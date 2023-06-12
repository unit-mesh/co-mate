package org.archguard.meta.dsl

import org.archguard.meta.AtomicAction


class NormalExampleRule : AtomicAction {
    fun pattern(regex: String) {

    }

    fun example(s: String) {

    }

}


infix fun <T> T.should(matcher: (T) -> Unit) = matcher(this)

// todo: add support for shouldNot
infix fun <T> T.shouldNot(matcher: (Unit) -> Unit) = should(matcher)


class Naming : AtomicAction {
    val filename: String = ""

    private val conditions = mutableListOf<(String) -> Boolean>()
    fun endWiths(vararg suffixes: String): (Any) -> Unit {
        conditions.add { file ->
            suffixes.any { file.endsWith(it) }
        }

        return { }
    }
}

class LayeredRule {
    private var pattern: String? = null
    private var namingRules: Naming? = null

    fun pattern(pattern: String, block: Naming.() -> Unit) {
        this.pattern = pattern
        this.namingRules = Naming().apply(block)
    }

    fun naming(function: Naming.() -> Unit): Naming {
        val rule = Naming()
        rule.function()
        return rule
    }

    fun dependsOn(s: String) {

    }
}

class DependencyRule {
    val rules = mutableListOf<Pair<String, String>>()
    infix fun String.dependedOn(to: String) {
        rules.add(this to to)
    }
}

class LayeredDeclaration : AtomicAction {
    val dependencyRules = HashMap<String, List<String>>()

    fun layer(name: String, function: LayeredRule.() -> Unit): LayeredRule {
        val rule = LayeredRule()
        rule.function()
        return rule
    }

    fun dependency(function: DependencyRule.() -> Unit): DependencyRule {
        val rule = DependencyRule()
        rule.function()
        return rule
    }

}

class NamingDeclaration {
    val `函数名`: String = ""

    fun startsWith(vararg symbols: String): (Unit) -> Unit {

    }
}

class BackendSpecDsl {
    fun project_name(function: NormalExampleRule.() -> Unit): NormalExampleRule {
        val rule = NormalExampleRule()
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

fun backend(init: BackendSpecDsl.() -> Unit): BackendSpecDsl {
    val html = BackendSpecDsl()
    html.init()
    return html
}
