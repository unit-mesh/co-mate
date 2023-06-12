package org.archguard.meta.dsl.foundation

import org.archguard.meta.base.AtomicAction

class Naming : AtomicAction {
    val filename: String = ""

    private val conditions = mutableListOf<(String) -> Boolean>()

    fun endWiths(vararg suffixes: String) {
        conditions.add { file ->
            suffixes.any { file.endsWith(it) }
        }
    }

    fun startsWith(vararg symbols: String) {
    }

    fun contains(vararg symbols: String) {
    }
}