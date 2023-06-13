package org.archguard.comate.uml

import chapi.domain.core.CodeDataStruct

class UmlConverter {
    fun byFile(element: CodeDataStruct): String {
        return """
            @startuml
            class ${element.NodeName} {
                ${element.Functions.joinToString("\n") { "${it.ReturnType} ${it.Name}()" }}
            }
            @enduml
        """.trimIndent()
    }

    /**
     * output example:
     * @startuml
     * package "com.example" {
     *     class TicketBooking
     * }
     * @enduml
     */
    fun byPackage(elements: List<CodeDataStruct>): String {
        val packageMap = elements.groupBy { it.Package }

        return """
            @startuml
            ${
            packageMap.map { (packageName, structs) ->
                """
            package "$packageName" {
                ${structs.joinToString("\n") { "class ${it.NodeName}" }}
            }
            """
            }.joinToString("\n")
        }
            @enduml
        """.trimIndent()
    }
}
