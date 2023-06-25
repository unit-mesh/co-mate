package org.archguard.spec.lang.foundation.rule

import org.archguard.spec.base.Rule
import org.archguard.spec.base.RuleResult
import org.archguard.spec.element.FoundationElement

class DependencyRule : Rule<FoundationElement> {
    override val actionName: String get() = "DependencyRule"
    var rules: HashMap<String, List<String>> = hashMapOf()

    infix fun String.dependedOn(to: String) {
        val list = rules.getOrDefault(this, listOf())
        rules[this] = list + to
    }

    override fun exec(input: FoundationElement): List<RuleResult> {
        if (input.layeredDefines.isEmpty() || input.ds.isEmpty() || this.rules.isEmpty()) {
            return listOf()
        }

        val results = mutableListOf<RuleResult>()

        val layerRegexMap: Map<String, Regex> = input.layeredDefines.associate {
            it.name to Regex(it.pattern!!)
        }

        this.rules.forEach { (from, targetPkg) ->
            val currentPkg = layerRegexMap[from] ?: return@forEach
            val targetList = targetPkg.map { to -> layerRegexMap[to] }

            input.ds.forEach { ds ->
                if (currentPkg.matches(ds.Package)) {
                    if (ds.Imports.isEmpty()) {
                        return@forEach
                    }

                    val hasMatch = targetList.any { target ->
                        ds.Imports.any { imp ->
                            target!!.matches(imp.Source.substringBeforeLast("."))
                        }
                    }

                    if (!hasMatch) {
                        val rule = "Package $from should not depend on ${targetPkg.joinToString(", ")}"
                        results.add(RuleResult(this.actionName, rule, false, ds.Package + "." + ds.NodeName))
                    }
                }
            }
        }

        return results
    }
}