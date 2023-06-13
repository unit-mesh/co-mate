package org.archguard.comate.uml

import chapi.domain.core.CodeDataStruct

class UmlConverter {
    fun fromChapi(element: CodeDataStruct): String {
        return """
            @startuml
            ${element.NodeName} {
            
            }
            @enduml
        """.trimIndent()
    }
}
