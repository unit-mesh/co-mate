package org.archguard.spec

import org.archguard.meta.AtomicAction

interface Partitioner {
    fun partition(): List<AtomicAction>
}
