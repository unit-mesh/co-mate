package org.archguard.meta.dsl.foundation

import org.archguard.meta.base.AtomicAction
import org.archguard.meta.base.RuleResult


typealias NamingRule = Naming.() -> Boolean

enum class CompareType {
    END_WITHS,
    STARTS_WITH,
    CONTAINS
}

data class DelayCompare(var left: String, val compareType: CompareType, val right: List<String>) {
    fun compare(): Boolean {
        return when (compareType) {
            CompareType.END_WITHS -> {
                right.any { left.endsWith(it) }
            }

            CompareType.STARTS_WITH -> {
                right.any { left.startsWith(it) }
            }

            CompareType.CONTAINS -> {
                right.any { left.contains(it) }
            }
        }
    }
}

class Naming : AtomicAction<String> {
    override val actionName: String = "Naming"
    val name: Boolean = true
    var string: String = ""

    private var delayCompare: DelayCompare? = null

    fun endWiths(vararg suffixes: String) {
        this.delayCompare = DelayCompare(string, CompareType.END_WITHS, suffixes.toList())
    }

    fun startsWith(vararg symbols: String) {
        this.delayCompare = DelayCompare(string, CompareType.STARTS_WITH, symbols.toList())
    }

    fun contains(vararg symbols: String) {
        this.delayCompare = DelayCompare(string, CompareType.CONTAINS, symbols.toList())
    }

    fun delayBlock(block: NamingRule) {
        block(this)
    }

    override fun exec(input: String): List<RuleResult> {
        this.delayCompare!!.left = input
        val compare = delayCompare!!.compare()
        return listOf(RuleResult(this.actionName, "Naming exec: $input, compareType: $delayCompare", compare))
    }
}