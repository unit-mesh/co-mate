package org.archguard.comate.model

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

    fun byFiles(elements: List<CodeDataStruct>): String {
        return """
            @startuml
${renderElements(elements)}
            @enduml
        """.trimIndent()
    }

    private fun renderElements(elements: List<CodeDataStruct>) =
        elements.joinToString("\n") { byElement(it) }

    private fun byElement(element: CodeDataStruct) =
        """            class ${element.NodeName} {
${renderFunctions(element)}
            }"""

    private fun renderFunctions(element: CodeDataStruct) =
        element.Functions.joinToString("\n") { "                ${it.ReturnType} ${it.Name}()" }

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
