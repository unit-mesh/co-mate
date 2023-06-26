package org.archguard.architecture

import org.junit.jupiter.api.Test

class AgentDslTest {
    @Test
    fun demo() {
        exec {
            workspace("payment-service")

            group("com.banking.payment")

            archStyle("ddd")

            operation {
                createClass(
                    "com.banking.payment", "PaymentService", """
class PaymentService {
    fun pay() {
        println("pay")
    }
}
            """.trimIndent()
                )

            }
        }
    }
}