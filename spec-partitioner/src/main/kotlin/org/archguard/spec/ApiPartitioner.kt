package org.archguard.spec

import org.archguard.comate.smart.Semantic
import org.archguard.meta.AtomicAction

val apiEmbeddingMap: Map<String, FloatArray>
    get() {
        val map = mutableMapOf<String, FloatArray>()
        val uris = listOf("URI Construction", "Construction")
        val statusCode = listOf("HTTP response status codes", "Status Code", "Request/Response Status Code")
        val httpMethods = listOf("HTTP Methods", "HTTP Method", "Method")
        val security = listOf("Security", "Authentication", "Authorization")


        return map
    }

class ApiPartitioner(document: String) : Partitioner {
    val embeddingMap: Map<String, FloatArray> = apiEmbeddingMap

    override fun partition(): List<AtomicAction> {
        return listOf()
    }
}
