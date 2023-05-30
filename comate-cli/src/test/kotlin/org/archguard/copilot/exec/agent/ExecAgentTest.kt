package org.archguard.copilot.exec.agent

import org.junit.jupiter.api.Test

class ExecAgentTest {
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