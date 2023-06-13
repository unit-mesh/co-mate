package org.archguard.meta.dsl

import org.archguard.meta.base.*
import org.archguard.meta.dsl.foundation.declaration.LayeredDeclaration
import org.archguard.meta.dsl.foundation.declaration.NamingDeclaration
import org.archguard.meta.dsl.foundation.declaration.ProjectNameDeclaration
import org.archguard.meta.dsl.foundation.rule.LayeredDef
import org.archguard.meta.model.FoundationElement

class DependencyRule : Rule<FoundationElement> {
    override val actionName: String get() = "DependencyRule"
    private val rules: HashMap<String, List<String>> = hashMapOf()

    infix fun String.dependedOn(to: String) {
        val list = rules.getOrDefault(this, listOf())
        rules[this] = list + to
    }

    override fun exec(input: FoundationElement): List<RuleResult> {
        val results = mutableListOf<RuleResult>()

        val layerRegexMap: Map<String, Regex> = input.layeredDefs.map {
            it.name to Regex(it.pattern!!)
        }.toMap()

        this.rules.forEach { (from, targetPkg) ->
            val currentPkg = layerRegexMap[from] ?: return@forEach
            val targetList = targetPkg.map { to -> layerRegexMap[to] }

            input.ds.forEach { ds ->
                val pkg = ds.Package
                if (currentPkg.matches(pkg)) {
                    if (ds.Imports.isEmpty()) {
                        return@forEach
                    }

                    val hasMatch = targetList.any { target ->
                        ds.Imports.any { imp ->
                            target!!.matches(imp.Source.substringBeforeLast("."))
                        }
                    }

                    if (!hasMatch) {
                        val rule = "Package ${ds.Package} should not depend on ${targetPkg.joinToString(", ")}"
                        results.add(RuleResult(this.actionName, rule, false))
                    }
                }
            }
        }

        return results
    }
}

@SpecDsl
class FoundationSpec : Spec<FoundationElement> {
    private val declarations = mutableListOf<BaseDeclaration<FoundationElement>>()

    fun project_name(function: ProjectNameDeclaration.() -> Unit): ProjectNameDeclaration {
        val rule = ProjectNameDeclaration()
        rule.function()

        declarations.add(rule)
        return rule
    }

    fun layered(function: LayeredDeclaration.() -> Unit): LayeredDeclaration {
        val rule = LayeredDeclaration()
        rule.function()

        declarations.add(rule)
        return rule
    }

    fun naming(function: NamingDeclaration.() -> Unit): NamingDeclaration {
        val rule = NamingDeclaration()
        rule.function()

        declarations.add(rule)
        return rule
    }

    override fun setVerifier(ruleVerifier: LlmRuleVerifier) {

    }

    override fun exec(element: FoundationElement): List<RuleResult> {
        val rules = declarations.map { declaration ->
            declaration.rules(element)
        }.flatten()

        // update context
        element.layeredDefs = rules.filterIsInstance<LayeredDef>()

        return rules.map { rule ->
            rule.exec(element) as List<RuleResult>
        }.flatten()
    }
}

fun foundation(init: FoundationSpec.() -> Unit): FoundationSpec {
    val spec = FoundationSpec()
    spec.init()
    return spec
}
