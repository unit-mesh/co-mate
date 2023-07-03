package org.archguard.comate.server.prompt

import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.archguard.comate.command.fakeComateContext
import org.archguard.comate.dynamic.DynamicContextFactory
import org.archguard.comate.server.prompt.dto.PromptToolingReq
import org.archguard.comate.server.prompt.dto.PromptToolingRes
import org.archguard.comate.server.prompt.model.BaseTool

fun Route.routeForPrompt() {
    val fakeFactory = DynamicContextFactory(fakeComateContext())
    val tools: List<BaseTool> = fakeFactory.tools().map {
        BaseTool(it.key, it.value)
    }

    get("/api/prompt/tooling") {
        call.respond(PromptToolingRes("", tools))
    }
    post("/api/prompt/tooling") {
        val tooling = call.receive<PromptToolingReq>()
        val prompt = PromptingWrapper().functionSearch(tooling.text, tools)

        call.respond(PromptToolingRes(prompt, tools))
    }
}
