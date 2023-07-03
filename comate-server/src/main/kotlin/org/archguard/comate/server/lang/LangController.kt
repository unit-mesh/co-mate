package org.archguard.comate.server.lang

import io.ktor.server.routing.*

fun Route.routeForLang() {
    post("/api/lang/verify") {
        // todo: verify the code
    }
}