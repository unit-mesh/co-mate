package org.archguard.meta

import org.junit.jupiter.api.Test

class RestApiGovernanceTest {
    @Test
    fun spec_checking() {
        val governance = api_governance {
            uri_construction {
                rule("http:\\/\\/[a-zA-Z0-9.\\-]+:[0-9]+\\/api\\/[a-zA-Z0-9]+\\/v[0-9]+\\/[a-zA-Z0-9\\/\\-]+")
                example("http://127.0.0.1:8080/api/petstore/v1/pets/dogs")
            }

            http_action("GET", "POST", "PUT", "DELETE")
            status_code(200, 201, 202, 204, 400, 401, 403, 404, 500, 502, 503, 504)

            security(
                """
Token Based Authentication (Recommended) Ideally, microservices should be stateless so the service instances can be scaled out easily and the client requests can be routed to multiple independent service providers. A token based authentication mechanism should be used instead of session based authentication
            """.trimIndent()
            )
        }

        governance.exec(RestApi("http://127.0.0.1:8080/api/petstore/v1/pets/dogs"))
    }
}
