package org.archguard.meta.dsl.foundation.rule

import org.archguard.architecture.style.NamingStyle
import org.archguard.meta.base.Rule
import org.archguard.meta.base.RuleResult
import org.archguard.meta.dsl.foundation.declaration.NamingTarget
import org.archguard.meta.dsl.foundation.expression.NamingExpression
import org.archguard.meta.model.FoundationElement

class NamingStyleRule(val target: NamingTarget) : Rule<FoundationElement> {
    override val actionName: String get() = "Naming for " + target.name
    private var filterPattern: Regex = Regex(".*")
    private var namingRule: NamingRule? = null
    private var namingStyle = NamingStyle.CamelCase

    fun style(style: String) {
        if (!NamingStyle.contains(style)) {
            throw IllegalArgumentException("Unknown naming style: $style. Supported styles: ${NamingStyle.valuesString()}")
        }

        namingStyle = NamingStyle.valueOf(style)
    }

    /**
     * for filter element by regex
     */
    fun pattern(pattern: String, block: NamingExpression) {
        this.filterPattern = Regex(pattern)
        val namingRule = NamingRule()
        namingRule.delayBlock(block)
        this.namingRule = namingRule
    }

    override fun exec(input: FoundationElement): List<RuleResult> {
        val results = mutableListOf<RuleResult>()

        results += verifyNodeName(input)

        if (namingRule != null) {
            input.ds
                .filter {
                    filterPattern.matches(it.NodeName)
                }
                .map {
                    namingRule!!.exec(it.NodeName)
                }
                .flatten().forEach {
                    results.add(it)
                }
        }

        return results
    }

    private fun verifyNodeName(input: FoundationElement): List<RuleResult> {
        return input.ds.map {
            val rule = "Level: $target, NamingStyle: $namingStyle"

            when (target) {
                NamingTarget.Package -> {
                    listOf(RuleResult(actionName, rule, namingStyle.isValid(it.Package)))
                }

                NamingTarget.Class -> {
                    listOf(RuleResult(actionName, rule, namingStyle.isValid(it.NodeName)))
                }

                NamingTarget.Function -> {
                    it.Functions.map {
                        RuleResult(actionName, rule, namingStyle.isValid(it.Name))
                    }
                }
            }
        }.flatten()
    }
}