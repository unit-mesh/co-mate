package org.archguard.meta.dsl

import org.archguard.architecture.style.NamingStyle
import org.archguard.meta.AtomicAction
import org.archguard.meta.baserule.PatternWithExampleRule


class ProjectNameDecl : PatternWithExampleRule {
    override fun pattern(regex: String) {

    }

    override fun example(sample: String) {

    }

}


class Naming : AtomicAction {
    val filename: String = ""

    private val conditions = mutableListOf<(String) -> Boolean>()

    fun endWiths(vararg suffixes: String) {
        conditions.add { file ->
            suffixes.any { file.endsWith(it) }
        }
    }

    fun startsWith(vararg symbols: String) {
    }

    fun contains(vararg symbols: String) {
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

class NamingItem(val name: String = "") {
    fun style(style: String) {
        if (!NamingStyle.contains(style)) {
            throw IllegalArgumentException("Unknown naming style: $style. Supported styles: ${NamingStyle.valuesString()}")
        }
    }

    fun pattern(pattern: String, block: Naming.() -> Unit) {
        Naming().apply(block)
    }
}

class NamingDeclaration {
    fun class_level(function: NamingItem.() -> Unit): NamingItem {
        val rule = NamingItem()
        rule.function()
        return rule
    }

    fun function_level(function: NamingItem.() -> Unit): NamingItem {
        val rule = NamingItem()
        rule.function()
        return rule
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
