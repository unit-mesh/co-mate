package org.archguard.meta.dsl.foundation

import org.archguard.meta.base.AtomicAction
import org.archguard.meta.base.RuleResult


typealias NamingRule = Naming.() -> Boolean

class Naming : AtomicAction<String> {
    override val actionName: String = "Naming"
    val name: Boolean = true
    var block: NamingRule? = null

    var string: String = ""

    fun endWiths(vararg suffixes: String): Boolean {
        return suffixes.any { string.endsWith(it) }
    }

    fun startsWith(vararg symbols: String): Boolean {
        return symbols.any { string.startsWith(it) }
    }

    fun contains(vararg symbols: String): Boolean {
        return symbols.any { string.contains(it) }
    }

    fun delayBlock(block: NamingRule) {
        this.block = block
    }

    override fun exec(input: String): List<RuleResult> {
        println("input: $input")
        if (block != null) {
            println(block!!(this))
        }
        return listOf()
    }
}