package org.archguard.comate.uml

import chapi.domain.core.CodeDataStruct

class UmlConverter {
    private val indent = """            """

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
        val packageMap = elements.filter { it.Package.isNotEmpty() }.groupBy { it.Package!! }

        return """
${indent}@startuml
${byPackage(packageMap)}
${indent}@enduml
        """.trimIndent()
    }

    private fun byPackage(packageMap: Map<String, List<CodeDataStruct>>): String {
        return packageMap.map { (packageName, structs) ->
            """${indent}package "$packageName" {
${structs.joinToString("\n") { "$indent    class ${it.NodeName}" }}
${indent}}"""
        }.joinToString("\n")
    }
}
