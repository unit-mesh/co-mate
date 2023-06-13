package org.archguard.comate.uml

import chapi.domain.core.CodeDataStruct

class UmlConverter {
    private val indent = """            """

    fun byFile(element: CodeDataStruct): String {
        return """
            @startuml
${byElement(element)}
            @enduml
        """.trimIndent()
    }

    private fun byElement(element: CodeDataStruct) =
"""            class ${element.NodeName} {
${element.Functions.joinToString("\n") { "                ${it.ReturnType} ${it.Name}()" }}
            }"""

    fun byFiles(elements: List<CodeDataStruct>): String {
        return """
            @startuml
${elements.joinToString("\n") { byElement(it) }}
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
        val packageMap = elements.filter { it.Package.isNotEmpty() }.groupBy { it.Package }

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
