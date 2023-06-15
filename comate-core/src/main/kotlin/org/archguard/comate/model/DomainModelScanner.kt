package org.archguard.comate.model

import chapi.domain.core.CodeDataStruct

interface DomainModelScanner {
    fun scan(): List<CodeDataStruct>
    fun toUml(ds: List<CodeDataStruct>): String = UmlConverter().byFiles(ds)
}

class DddDomainModelScanner(
    private val ds: List<CodeDataStruct>,
) : DomainModelScanner {
    override fun scan(): List<CodeDataStruct> {
        val regex = Regex("(domain[s]?|model[s]?)(\\.)?", RegexOption.IGNORE_CASE)
        return ds.filter { regex.containsMatchIn(it.Package) }
    }
}

/**
 * It should be ThreeLayered, but MVC is more popular called.
 */
class MvcDomainModelScanner(
    private val ds: List<CodeDataStruct>,
) : DomainModelScanner {
    override fun scan(): List<CodeDataStruct> {
        val regex = Regex("(model[s]?|domain)(\\.)?", RegexOption.IGNORE_CASE)
        return ds.filter { regex.containsMatchIn(it.Package) }
    }
}