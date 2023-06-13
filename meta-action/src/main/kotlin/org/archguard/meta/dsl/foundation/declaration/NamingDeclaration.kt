package org.archguard.meta.dsl.foundation.declaration

import org.archguard.architecture.style.NamingStyle
import org.archguard.meta.base.AtomicAction
import org.archguard.meta.base.BaseDeclaration
import org.archguard.meta.base.RuleResult
import org.archguard.meta.dsl.foundation.rule.NamingExpression
import org.archguard.meta.dsl.foundation.rule.NamingRule
import org.archguard.meta.model.FoundationElement

enum class NamingTarget {
    Package,
    Class,
    Function,
}

class NamingItem(val target: NamingTarget) : AtomicAction<FoundationElement> {
    override val actionName: String get() = "Naming for " + target.name
    private var filter: Regex = Regex(".*")
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
        this.filter = Regex(pattern)
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
                    filter.matches(it.NodeName)
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

class NamingDeclaration : BaseDeclaration<FoundationElement> {
    val rules: MutableList<AtomicAction<FoundationElement>> = mutableListOf()

    fun class_level(function: NamingItem.() -> Unit): NamingItem {
        val rule = NamingItem(NamingTarget.Class)
        rule.function()

        rules.add(rule)
        return rule
    }

    fun function_level(function: NamingItem.() -> Unit): NamingItem {
        val rule = NamingItem(NamingTarget.Function)
        rule.function()

        rules.add(rule)
        return rule
    }

    override fun rules(): List<AtomicAction<FoundationElement>> {
        return rules
    }
}