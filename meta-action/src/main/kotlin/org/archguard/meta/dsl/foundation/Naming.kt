package org.archguard.meta.dsl.foundation

import org.archguard.meta.base.AtomicAction
import org.archguard.meta.base.RuleResult

class Naming : AtomicAction<String> {
    override val name: String = "Naming"

    var string: String = ""

    fun endWiths(vararg suffixes: String) : Boolean {
        return suffixes.any { string.endsWith(it) }
    }

    fun startsWith(vararg symbols: String): Boolean {
        return symbols.any { string.startsWith(it) }
    }

    fun contains(vararg symbols: String): Boolean {
        return symbols.any { string.contains(it) }
    }

    override fun exec(input: String): List<RuleResult> {
        return listOf()
    }
}