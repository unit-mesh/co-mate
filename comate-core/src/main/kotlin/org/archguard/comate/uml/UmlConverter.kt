package org.archguard.comate.uml

import chapi.domain.core.CodeDataStruct
import chapi.domain.core.CodeFunction

class UmlConverter {
    fun fromChapi(element: CodeDataStruct): String {
        val classBlock = """class ${element.NodeName} {
                ${functionBlock(element.Functions)}
            }"""

        return wrapper(classBlock)
    }

    private fun functionBlock(codeFunctions: List<CodeFunction>) =
        codeFunctions.joinToString("\n") { "${it.ReturnType} ${it.Name}()" }

    private fun wrapper(classBlock: String) = "@startuml\n$classBlock\n@enduml"
}
