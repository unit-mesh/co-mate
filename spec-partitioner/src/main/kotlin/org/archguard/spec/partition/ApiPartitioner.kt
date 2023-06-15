package org.archguard.spec.partition

import org.archguard.comate.smart.Semantic
import org.archguard.spec.lang.ApiRuleType
import org.archguard.spec.base.Rule

val apiEmbeddingMap: Map<FloatArray, ApiRuleType>
    get() {
        val map = mutableMapOf<FloatArray, ApiRuleType>()
        val uris = listOf("URI Construction", "Construction")
        val statusCode = listOf("HTTP response status codes", "Status Code", "Request/Response Status Code")
        val httpMethods = listOf("HTTP Methods", "HTTP Method", "Method")
        val security = listOf("Security", "Authentication", "Authorization")
        val misc = listOf("Miscellaneous", "Misc")

        val semantic = Semantic.create()
        uris.forEach {
            map[semantic.embed(it)] = ApiRuleType.URI_CONSTRUCTION
        }
        statusCode.forEach {
            map[semantic.embed(it)] = ApiRuleType.STATUS_CODE
        }
        httpMethods.forEach {
            map[semantic.embed(it)] = ApiRuleType.HTTP_ACTION
        }
        security.forEach {
            map[semantic.embed(it)] = ApiRuleType.SECURITY
        }
        misc.forEach {
            map[semantic.embed(it)] = ApiRuleType.MISC
        }

        return map
    }

class ApiPartitioner(document: String) : Partitioner {
//    val embeddingMap: Map<FloatArray, ApiRuleType> = apiEmbeddingMap

    override fun partition(): List<Rule<Any>> {
        return listOf()
    }
}
