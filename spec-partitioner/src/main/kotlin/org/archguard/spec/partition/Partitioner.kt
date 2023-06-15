package org.archguard.spec.partition

import org.archguard.spec.base.Rule

interface Partitioner {
    fun partition(): List<Rule<Any>>
}
