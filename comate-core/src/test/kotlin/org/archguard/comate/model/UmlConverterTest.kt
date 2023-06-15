package org.archguard.comate.model

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

        val uml = converter.byFile(dataStruct)
        assertEquals(
            """
            @startuml
            class TicketBooking {
                void reverse()
            }
            @enduml
        """.trimIndent(), uml
        )
    }

    @Test
    fun should_render_correct_by_files() {
        val converter = UmlConverter()
        val ds1 = CodeDataStruct("TicketBooking")
        ds1.Functions += CodeFunction("reverse", ReturnType = "void")
        val ds2 = CodeDataStruct("Booking", Package = "com.example")
        ds2.Functions += CodeFunction("reverse", ReturnType = "void")

        val uml = converter.byFiles(listOf(ds1, ds2))
        assertEquals(
            """
            @startuml
            class TicketBooking {
                void reverse()
            }
            class Booking {
                void reverse()
            }
            @enduml
        """.trimIndent(), uml
        )
    }

    @Test
    fun should_success_load_from_chapi_with_package() {
        val converter = UmlConverter()
        val dataStruct = CodeDataStruct("TicketBooking", Package = "com.example")
        dataStruct.Functions += CodeFunction("reverse", ReturnType = "void")

        val uml = converter.byPackage(listOf(dataStruct))
        assertEquals(
            """
            @startuml
            package "com.example" {
                class TicketBooking
            }
            @enduml
        """.trimIndent(), uml
        )
    }
}