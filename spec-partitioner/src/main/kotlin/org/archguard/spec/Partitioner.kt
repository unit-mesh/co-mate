package org.archguard.spec

import org.archguard.meta.base.AtomicAction

interface Partitioner {
    fun partition(): List<AtomicAction>
}
