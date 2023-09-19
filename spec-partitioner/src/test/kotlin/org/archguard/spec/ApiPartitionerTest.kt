package org.archguard.spec

import org.archguard.spec.partition.ApiPartitioner
import org.junit.jupiter.api.Test

class ApiPartitionerTest {
    @Test
    fun sample_for_split() {
        val partitioner = ApiPartitioner()

        partitioner.partition()
    }
}