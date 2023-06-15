package org.archguard.comate.model

import chapi.domain.core.CodeDataStruct
import chapi.domain.core.CodeFunction
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class DomainModelScannerTest {
    @Test
    fun should_return_empty_list_when_no_domain_model_found() {
        val ds = listOf<CodeDataStruct>()
        val scanner = DddDomainModelScanner(ds)
        val result = scanner.scan()
        assertEquals(0, result.size)
    }

    @Test
    fun should_return_one_result_when_has_match_model() {
        val ds = listOf(
            CodeDataStruct(
                NodeName = "TicketBooking",
                Package = "com.example.domain",
                Functions = listOf(
                    CodeFunction(
                        Name = "book",
                        ReturnType = "void"
                    )
                )
            )
        )
        val scanner = DddDomainModelScanner(ds)
        val result = scanner.scan()
        assertEquals(1, result.size)
    }

    @Test
    fun should_return_two_result_when_has_match_models() {
        val ds = listOf(
            CodeDataStruct(
                NodeName = "TicketBooking",
                Package = "com.example.domain",
                Functions = listOf(
                    CodeFunction(
                        Name = "book",
                        ReturnType = "void"
                    )
                )
            ),
            CodeDataStruct(
                NodeName = "TicketBooking",
                Package = "com.example.domains",
                Functions = listOf(
                    CodeFunction(
                        Name = "book",
                        ReturnType = "void"
                    )
                )
            )
        )

        val scanner = DddDomainModelScanner(ds)
        val result = scanner.scan()
        assertEquals(2, result.size)
    }

    @Test
    fun should_convert_to_uml_when_has_one_domain_model() {
        val ds = listOf(
            CodeDataStruct(
                NodeName = "TicketBooking",
                Package = "com.example.domain",
                Functions = listOf(
                    CodeFunction(
                        Name = "book",
                        ReturnType = "void"
                    )
                )
            )
        )
        val scanner = DddDomainModelScanner(ds)
        val result = scanner.toUml(ds)
        assertEquals("""
            @startuml
            class TicketBooking {
                void book()
            }
            @enduml
        """.trimIndent(), result)
    }
}