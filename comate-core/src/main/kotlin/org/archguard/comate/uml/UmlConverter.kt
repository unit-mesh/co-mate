package org.archguard.comate.uml

import chapi.domain.core.CodeDataStruct

class UmlConverter {
    fun fromChapi(element: CodeDataStruct): String {
        return """
            @startuml
            class ${element.NodeName} {
                ${element.Functions.joinToString("\n") { "${it.ReturnType} ${it.Name}()" }}
            }
            @enduml
        """.trimIndent()
    }
}
