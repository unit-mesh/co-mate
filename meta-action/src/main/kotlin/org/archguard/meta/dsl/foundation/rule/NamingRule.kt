package org.archguard.meta.dsl.foundation.rule

import org.archguard.meta.base.Rule
import org.archguard.meta.base.RuleResult
import org.archguard.meta.dsl.foundation.expression.NamingExpression
import org.archguard.meta.dsl.matcher.CompareType
import org.archguard.meta.dsl.matcher.DelayCompare

class NamingRule : Rule<String> {
    override val actionName: String = "Naming"
    val name: Any = "<placeholder>"
    var string: String = ""

    private var delayCompare: DelayCompare? = null

    fun endWiths(vararg suffixes: String): DelayCompare {
        val compare = DelayCompare(string, CompareType.END_WITHS, suffixes.toList())
        this.delayCompare = compare
        return compare
    }

    fun startsWith(vararg symbols: String): DelayCompare {
        val compare = DelayCompare(string, CompareType.STARTS_WITH, symbols.toList())
        this.delayCompare = compare
        return compare
    }

    fun contains(vararg symbols: String): DelayCompare {
        val compare = DelayCompare(string, CompareType.CONTAINS, symbols.toList())
        this.delayCompare = compare
        return compare
    }

    fun delayBlock(block: NamingExpression) {
        block(this)
    }

    override fun exec(input: String): List<RuleResult> {
        this.delayCompare!!.left = input
        val compare = delayCompare!!.compare()
        return listOf(RuleResult(this.actionName, "Naming exec: $input, compareType: $delayCompare", compare))
    }
}