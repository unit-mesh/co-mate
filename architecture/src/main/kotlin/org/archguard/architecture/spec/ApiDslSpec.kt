package org.archguard.architecture.rest

/**
 * 20230606 version
 * based on: https://wiki.onap.org/display/DW/RESTful+API+Design+Specification
 * dynamic checking function with DSL ?
 *
 */
enum class ApiDslSpec {
    /**
     * URI structure http://[host]:[port]/api/{service name}]/v{version number}/{resource}
     */
    URIConstruction() {
        override fun description(): String {
            return """
                example: http://[host]:[port]/api/{service name}]/v{version number}/{resource}""".trimIndent()
        }

    },
    Versioning() {
        override fun description(): String {
            return """"""
        }
    },
    StatusCode() {
        /**
         * 200 – OK – Everything is working
         * 201 – OK – New resource has been created
         * 204 – OK – The resource was successfully deleted
         *
         * 304 – Not Modified – The client can use cached data
         *
         * 400 – Bad Request – The request was invalid or cannot be served. The exact error should be explained in the error payload. E.g. „The JSON is not valid“
         * 401 – Unauthorized – The request requires a user authentication
         * 403 – Forbidden – The server understood the request but is refusing it or the access is not allowed.
         * 404 – Not found – There is no resource behind the URI.
         * 422 – Unprocessable Entity – Should be used if the server cannot process the entity, e.g. if an image cannot be formatted or mandatory fields are missing in the payload.
         *
         * 500 – Internal Server Error – API developers should avoid this error. If an error occurs in the global catch blog, the stracktrace should be logged and not returned as in the response.
         */
        override fun description(): String {
            return """Status code is """
        }

    },
    Security() {
        /**
         * check is Authentication API ?
         */
        override fun description(): String {
            return ""
        }

    }
    ;

    abstract fun description(): String
}