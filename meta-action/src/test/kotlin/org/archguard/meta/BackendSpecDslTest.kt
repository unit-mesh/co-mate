package org.archguard.meta

import org.archguard.meta.dsl.backend
import org.junit.jupiter.api.Test

class BackendSpecDslTest {
    @Test
    fun spec_checking() {
        val governance = backend {
            project_name {
                pattern("^([a-z0-9-]+)-([a-z0-9-]+)-([a-z0-9-]+)(-common)?\$")
                example("system1-servicecenter1-microservice1")
            }

            layered {
                layer("application") {
//                    pattern(".*\\.model") { filename should endWiths("DTO", "Request", "Response") }
                }

                layer("domain") {
//                    pattern(".*\\.model") { filename should endWiths("Entity") }
//                    pattern(".*\\.model") { filename shouldNot endWiths("Entity") }
                }

                dependency {
                    "domain" dependedOn "infrastructure"
                    "application" dependedOn "domain"
                }
            }

            naming {
                style("CamelCase")
//                函数名 shouldNot contains("$")
            }
        }
    }
}