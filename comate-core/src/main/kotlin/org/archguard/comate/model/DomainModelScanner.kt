package org.archguard.comate.model

import chapi.domain.core.CodeDataStruct
import org.archguard.spec.execute.DomainModelScanner

class DddDomainModelScanner(
    private val ds: List<CodeDataStruct>,
) : DomainModelScanner {
    override fun toUml(ds: List<CodeDataStruct>): String = UmlConverter().byFiles(ds)
    override fun scan(): List<CodeDataStruct> {
        val regex = Regex("(domain[s]?|element[s]?)(\\.)?", RegexOption.IGNORE_CASE)
        return ds.filter { regex.containsMatchIn(it.Package) }
    }
}

/**
 * It should be ThreeLayered, but MVC is more popular called.
 */
class MvcDomainModelScanner(
    private val ds: List<CodeDataStruct>,
) : DomainModelScanner {
    override fun toUml(ds: List<CodeDataStruct>): String = UmlConverter().byFiles(ds)
    override fun scan(): List<CodeDataStruct> {
        val regex = Regex("(element[s]?|domain)(\\.)?", RegexOption.IGNORE_CASE)
        return ds.filter { regex.containsMatchIn(it.Package) }
    }
}

class DomainModelFactory {
    companion object {
        fun generate(layeredStyle: String, ds: List<CodeDataStruct>): String {
            val scanner = when (layeredStyle) {
                "ddd" -> DddDomainModelScanner(ds)
                "mvc" -> MvcDomainModelScanner(ds)
                else -> throw IllegalArgumentException("domain model type $layeredStyle is not supported")
            }

            return scanner.toUml(scanner.scan())
        }
    }
}