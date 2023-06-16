package org.archguard.spec.execute

import chapi.domain.core.CodeDataStruct

interface DomainModelScanner {
    fun scan(): List<CodeDataStruct>
    fun toUml(ds: List<CodeDataStruct>): String
}