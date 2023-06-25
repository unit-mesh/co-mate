package org.archguard.spec.lang.foundation.rule

import org.archguard.spec.base.Rule
import org.archguard.spec.base.RuleResult
import org.archguard.spec.element.FoundationElement

class DependencyRule : Rule<FoundationElement> {
    override val actionName: String get() = "DependencyRule"
    var rules: HashMap<String, List<String>> = hashMapOf()

    //
    var allTargetList: List<String> = listOf()

    infix fun String.dependedOn(to: String) {
        val list = rules.getOrDefault(this, listOf())
        rules[this] = list + to
        allTargetList = allTargetList + to
    }

    override fun exec(input: FoundationElement): List<RuleResult> {
        if (input.layeredDefines.isEmpty() || input.ds.isEmpty() || this.rules.isEmpty()) {
            return listOf()
        }

        val results = mutableListOf<RuleResult>()

        val layerRegexMap: Map<String, Regex> = input.layeredDefines.associate {
            it.name to Regex(it.pattern!!)
        }

        this.rules.forEach { (from, target) ->
            val currentPkg = layerRegexMap[from] ?: return@forEach

            val underTargetList = allTargetList.filter { !target.contains(it) }
                .map { layerRegexMap[it] }

            if (underTargetList.any { it == null }) {
                return@forEach
            }

            input.ds.forEachIndexed { _, ds ->
                if (currentPkg.matches(ds.Package)) {
                    if (ds.Imports.isEmpty()) {
                        return@forEachIndexed
                    }

                    val hasErrorDep = underTargetList.any { target ->
                        ds.Imports.any { imp ->
                            target!!.matches(imp.Source.substringBeforeLast("."))
                        }
                    }

                    if (hasErrorDep) {
                        val rule = "Package $from should not depend on ${target.joinToString(", ")}"
                        results.add(RuleResult(this.actionName, rule, false, ds.Package + "." + ds.NodeName))
                    }
                }
            }
        }

        return results
    }
}