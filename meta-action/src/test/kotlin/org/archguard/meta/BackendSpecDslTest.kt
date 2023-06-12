package org.archguard.meta

import org.archguard.meta.dsl.backend
import org.junit.jupiter.api.Test

class BackendSpecDslTest {
    @Test
    fun spec_checking() {
        val governance = backend {
            repository {
                pattern("^([a-z0-9-]+)-([a-z0-9-]+)-([a-z0-9-]+)(-common)?\$")
                example("system1-servicecenter1-microservice1")
            }

            layered {
                infrastructure {
                    dependsOn("repository")
                    pattern(".*\\.model") {
                        filename should endWiths("DTO")
                    }
                }
            }
        }
    }
}