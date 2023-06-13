package org.archguard.spec

import org.archguard.meta.base.Rule

interface Partitioner {
    fun partition(): List<Rule<Any>>
}
