package org.archguard.comate.server.action

import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.testing.*
import org.archguard.comate.command.fakeComateContext
import org.archguard.comate.connector.OpenAIConnector
import org.archguard.comate.server.prompt.PromptToolingReq
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class ComateActionKtTest {
    val expect = "https://github.com/archguard/co-mate"

    @Test
    fun should_correct_parse_url() {
        assertEquals(expect, parseUrlFromRequest(expect))
        assertEquals(expect, parseUrlFromRequest("'https://github.com/archguard/co-mate'"))
    }

    @Test
    fun should_return_bad_request_when_action_no_exist() = testApplication {
//        externalServices {
//            hosts("https://api.openai.com/") {
//                install(io.ktor.server.plugins.contentnegotiation.ContentNegotiation) {
//                    json()
//                }
//                routing {
//                    post("/v1/chat/completions") {
//                        call.respond("this is a demo from server")
//                    }
//                }
//            }
//        }

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
}