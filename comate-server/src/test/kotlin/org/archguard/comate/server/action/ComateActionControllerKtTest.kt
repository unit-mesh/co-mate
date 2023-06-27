package org.archguard.comate.server.action

import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.testing.*
import io.mockk.every
import io.mockk.mockk
import org.archguard.comate.connector.OpenAIConnector
import org.archguard.comate.server.action.dto.ToolingThought
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class ComateActionControllerKtTest {
    private val expect = "https://github.com/archguard/co-mate"

    @Test
    fun should_correct_parse_url() {
        assertEquals(expect, parseUrlFromRequest(expect))
        assertEquals(expect, parseUrlFromRequest("'https://github.com/archguard/co-mate'"))
    }

    @Test
    fun should_return_bad_request_when_action_no_exist() = testApplication {
        val client = createClient {
            install(ContentNegotiation) {
                json()
            }
        }

        val response = client.post("/api/action/tooling") {
            contentType(ContentType.Application.Json)
            setBody(ToolingThought("api governance", "2333", expect))
        }

        assertEquals(HttpStatusCode.BadRequest, response.status)
    }

    @Test
    fun should_handle_external_openai_calling() = testApplication {
        val connector = mockk<OpenAIConnector>()
        every { connector.prompt(any()) } returns "true"

        comateContext.connector = connector
        val client = createClient {
            install(ContentNegotiation) {
                json()
            }
        }

        val response = client.post("/api/action/tooling") {
            contentType(ContentType.Application.Json)
            setBody(ToolingThought("api governance", "rest_api_governance", expect))
        }

        assertEquals(HttpStatusCode.OK, response.status)
    }
}