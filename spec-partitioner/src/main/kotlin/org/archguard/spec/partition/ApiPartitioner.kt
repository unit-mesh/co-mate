package org.archguard.spec.partition

import org.archguard.comate.smart.Semantic
import org.archguard.spec.base.Rule
import org.archguard.spec.lang.restapi.ApiAtomicRule
import org.archguard.spec.lang.restapi.rule.*

// todo: is for checking the rule type
enum class ApiRuleType(rule: Class<out ApiAtomicRule>) {
    HTTP_ACTION(HttpActionRule::class.java),
    MISC(MiscRule::class.java),
    SECURITY(SecurityRule::class.java),
    STATUS_CODE(StatusCodeRule::class.java),
    URI_CONSTRUCTION(UriConstructionRule::class.java)
}

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

class ApiPartitioner : Partitioner {
    override fun partition(): List<Rule<Any>> {
        return listOf()
    }
}
