package org.archguard.comate.server.prompt

import io.ktor.client.call.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.testing.*
import org.archguard.comate.server.action.ToolingAction
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class PromptRoutesKtTest {
    @Test
    fun should_return_all_tooling_api() = testApplication {
        val response = client.get("/api/prompt/tooling")
        assertEquals(HttpStatusCode.OK, response.status)
        val body = response.bodyAsText()

        assertTrue(body.contains(ToolingAction.REST_API_GOVERNANCE.action))
        assertTrue(body.contains(ToolingAction.FOUNDATION_SPEC_GOVERNANCE.action))
    }

    @Test
    fun should_return_prompt_text_from_post() = testApplication {
        val client = createClient {
            install(ContentNegotiation) {
                json()
            }
        }

        val response = client.post("/api/prompt/tooling") {
            contentType(ContentType.Application.Json)
            setBody(PromptToolingReq("rest api governance"))
        }
        assertEquals(HttpStatusCode.OK, response.status)
        val body = response.body<PromptToolingRes>()

        println(body)
        assertTrue(body.prompt.contains("Answer the following questions as best you can."))
    }
}
