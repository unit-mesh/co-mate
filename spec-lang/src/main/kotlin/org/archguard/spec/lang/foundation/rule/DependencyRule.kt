package org.archguard.spec.lang.foundation.rule

import org.archguard.spec.base.Rule
import org.archguard.spec.base.RuleResult
import org.archguard.spec.element.FoundationElement

class DependencyRule : Rule<FoundationElement> {
    override val actionName: String get() = "DependencyRule"
    private val rules: HashMap<String, List<String>> = hashMapOf()

    infix fun String.dependedOn(to: String) {
        val list = rules.getOrDefault(this, listOf())
        rules[this] = list + to
    }

    override fun exec(input: FoundationElement): List<RuleResult> {
        val results = mutableListOf<RuleResult>()

        val layerRegexMap: Map<String, Regex> = input.layeredDefines.map {
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