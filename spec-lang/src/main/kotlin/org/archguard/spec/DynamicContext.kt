package org.archguard.spec

enum class DynamicContext(val value: String) {
    API_SPECIFICATION("ApiSpecification") {
        override fun explain(): String {
            return """ApiSpecification is a specification of a REST API.""".trimIndent()
        }

    },
    FOUNDATION_SPECIFICATION("FoundationSpecification") {
        override fun explain(): String {
            return """FoundationSpecification is a specification of a naming style, package naming, class naming..""".trimIndent()
        }
    },
    DOMAIN_SPECIFICATION("DomainSpecification") {
        override fun explain(): String {
            return """DomainSpecification is a specification of a domain model.""".trimIndent()
        }
    },
    SERVICE_MAP("ServiceMap") {
        override fun explain(): String {
            return """ServiceMap is a define for service map.""".trimIndent()
        }
    },
    DOMAIN_MODEL("DomainModel") {
        override fun explain(): String {
            return """DomainModel is a define for domain model.""".trimIndent()
        }
    }
    ;

    abstract fun explain(): String

    companion object {
        fun from(value: String): org.archguard.spec.DynamicContext? {
            return values().find { it.value == value }
        }

        fun build(values: List<String>): List<String> = values.mapNotNull(org.archguard.spec.DynamicContext.Companion::from).map {
            when (it) {
                org.archguard.spec.DynamicContext.API_SPECIFICATION -> {
                    "ApiSpecification"
                }

                org.archguard.spec.DynamicContext.FOUNDATION_SPECIFICATION -> {
                    "FoundationSpecification"
                }

                org.archguard.spec.DynamicContext.DOMAIN_SPECIFICATION -> {
                    "DomainSpecification"
                }

                org.archguard.spec.DynamicContext.SERVICE_MAP -> {
                    "ServiceMap"
                }

                org.archguard.spec.DynamicContext.DOMAIN_MODEL -> {
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