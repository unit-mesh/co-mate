package org.archguard.meta

import org.archguard.meta.dsl.backend
import org.archguard.meta.dsl.matcher.shouldBe
import org.archguard.meta.dsl.matcher.shouldNotBe
import org.junit.jupiter.api.Test

class BackendSpecTest {
    @Test
    fun spec_checking() {
        val governance = backend {
            project_name {
                pattern("^([a-z0-9-]+)-([a-z0-9-]+)-([a-z0-9-]+)(-common)?\$")
                example("system1-servicecenter1-microservice1")
            }

            layered {
                layer("application") {
                    pattern(".*\\.model") { filename shouldBe endWiths("DTO", "Request", "Response") }
                }

                layer("domain") {
                    pattern(".*\\.model") { filename shouldBe endWiths("Entity") }
                    pattern(".*\\.model") { filename shouldNotBe endWiths("Entity") }
                }

                dependency {
                    "domain" dependedOn "infrastructure"
                    "application" dependedOn "domain"
                }
            }

            naming {
                class_level {
                    style("CamelCase")
                    pattern(".*") { name shouldNotBe contains("$") }
                }
                function_level {
                    style("CamelCase")
                    pattern(".*") { name shouldNotBe contains("$") }
                }
            }
        }
    }
}