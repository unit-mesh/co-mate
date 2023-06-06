package org.archguard.architecture.rest

/**
 * 20230606 version
 * based on: https://wiki.onap.org/display/DW/RESTful+API+Design+Specification
 * dynamic checking function with DSL ?
 *
 */
enum class ApiDocumentGovernance {
    URIConstruction() {
        override fun checking(): String {
            TODO("Not yet implemented")
        }

    },
    Versioning() {
        override fun checking(): String {
            TODO("Not yet implemented")
        }
    },
    StatusCode() {
        override fun checking(): String {
            TODO("Not yet implemented")
        }

    },
    Security() {
        override fun checking(): String {
            TODO("Not yet implemented")
        }

    }
    ;

    abstract fun checking(): String
}