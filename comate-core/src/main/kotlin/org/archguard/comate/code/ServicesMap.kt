package org.archguard.comate.code

import chapi.domain.core.CodeDataStruct
import org.archguard.scanner.analyser.ApiCallAnalyser
import org.archguard.scanner.core.sourcecode.ContainerService
import org.archguard.scanner.core.sourcecode.SourceCodeContext
import org.archguard.spec.element.RestApiElement

class ServicesMap {
    companion object {
        fun scanApis(
            codeDataStructs: List<CodeDataStruct>?,
            codeContext: SourceCodeContext,
        ): List<RestApiElement> {
            val services: List<ContainerService> = if (codeDataStructs != null) {
                ApiCallAnalyser(codeContext).analyse(codeDataStructs)
            } else {
                listOf()
            }

            val apis = services.flatMap {
                it.resources.map { resource ->
                    RestApiElement(
                        uri = resource.sourceUrl,
                        httpAction = resource.sourceHttpMethod.uppercase(),
                        statusCodes = listOf(200)
                    )
                }
            }
            return apis
        }
    }
}

