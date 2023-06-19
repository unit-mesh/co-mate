package org.archguard.comate.dynamic

import chapi.domain.core.CodeDataStruct
import org.archguard.comate.code.packageInString
import org.archguard.comate.command.ComateContext
import org.archguard.comate.document.ReadmeParser
import org.archguard.comate.model.DomainModelFactory
import org.archguard.spec.lang.DomainSpec
import org.archguard.spec.lang.FoundationSpec
import org.archguard.spec.lang.RestApiSpec

enum class SecondContext(val value: String) {
    SERVICE_MAP("ServiceMap") {
        override fun explain(): String {
            return """ServiceMap is a define for service map.""".trimIndent()
        }
    },
    LAYERED_STYLE("LayeredStyle") {
        override fun explain(): String {
            return """LayeredStyle is a defined for layered architecture.""".trimIndent()
        }
    };

    abstract fun explain(): String
}

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
    PACKAGE_INFO("PackageInfo") {
        override fun explain(): String {
            return """PackageInfo is a define for ALL package info.""".trimIndent()
        }
    },
    PROJECT_DEPENDENCY("ProjectDependency") {
        override fun explain(): String {
            return """ProjectDependency include all used defines""".trimIndent()
        }
    },
    README("README") {
        override fun explain(): String {
            return """README file contain project introduction.""".trimIndent()
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
        private fun from(value: String): DynamicContext? {
            return values().find { it.value == value }
        }

        fun build(values: List<String>, workspace: ComateContext): List<String> =
            values.mapNotNull(Companion::from).map {
                // TODO: load specification from file
                when (it) {
                    REST_API_SPECIFICATION -> RestApiSpec().default()
                    FOUNDATION_SPECIFICATION -> FoundationSpec().default()
                    DOMAIN_SPECIFICATION -> DomainSpec().default()

                    // TODO: load service map from file
//                    SERVICE_MAP -> "ServiceMap"
//                    LAYERED_STYLE -> "LayeredStyle"
                    DOMAIN_MODEL -> DomainModelFactory.generate("mvc", workspace.fetchDs())
                    PACKAGE_INFO -> CodeDataStruct.packageInString(workspace.fetchDs())
                    README -> ReadmeParser.introduction(workspace.workdir)
                    PROJECT_DEPENDENCY -> TODO()
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