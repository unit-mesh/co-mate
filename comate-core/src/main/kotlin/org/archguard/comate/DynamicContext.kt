package org.archguard.comate

import org.archguard.spec.lang.DomainSpec
import org.archguard.spec.lang.FoundationSpec
import org.archguard.spec.lang.RestApiSpec

enum class DynamicContext(val value: String) {
    REST_API_SPECIFICATION("ApiSpecification") {
        override fun explain(): String {
            return """ApiSpecification is a specification of a REST API.""".trimIndent()
        }
    },
    FOUNDATION_SPECIFICATION("FoundationSpecification") {
        override fun explain(): String {
            return """FoundationSpecification is a specification of layered architecture, naming style, package naming, class naming.""".trimIndent()
        }
    },
    DOMAIN_SPECIFICATION("DomainSpecification") {
        override fun explain(): String {
            return """DomainSpecification is a specification of a domain model element.""".trimIndent()
        }
    },
    SERVICE_MAP("ServiceMap") {
        override fun explain(): String {
            return """ServiceMap is a define for service map.""".trimIndent()
        }
    },
    DOMAIN_MODEL("DomainModel") {
        override fun explain(): String {
            return """DomainModel is a define for domain element.""".trimIndent()
        }
    }
    ;

    abstract fun explain(): String

    companion object {
        fun from(value: String): DynamicContext? {
            return values().find { it.value == value }
        }

        fun build(values: List<String>): List<String> = values.mapNotNull(Companion::from).map {
            // TODO: load specification from file
            when (it) {
                REST_API_SPECIFICATION -> RestApiSpec().default()
                FOUNDATION_SPECIFICATION -> FoundationSpec().default()
                DOMAIN_SPECIFICATION -> DomainSpec().default()

                SERVICE_MAP -> {
                    // TODO: load service map from file
                    "ServiceMap"
                }

                DOMAIN_MODEL -> {
                    "DomainModel"
                }
            }
        }

        /**
         * With this method, you can get all the explanations of the context.
         */
        fun explains(): HashMap<String, String> {
            val map = HashMap<String, String>()
            values().forEach {
                map[it.value] = it.explain()
            }

            return map
        }
    }
}