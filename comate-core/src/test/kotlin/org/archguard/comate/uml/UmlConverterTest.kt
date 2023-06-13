package org.archguard.comate.uml

import chapi.domain.core.CodeDataStruct
import chapi.domain.core.CodeFunction
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class UmlConverterTest {

    @Test
    fun should_success_load_from_chapi() {
        val converter = UmlConverter()
        val dataStruct = CodeDataStruct("TicketBooking")
        dataStruct.Functions += CodeFunction("reverse", ReturnType = "void")

        val uml = converter.fromChapi(dataStruct)
        assertEquals("""
            @startuml
            class TicketBooking {
                void reverse()
            }
            @enduml
        """.trimIndent(), uml)
    }
}