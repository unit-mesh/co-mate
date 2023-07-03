package org.archguard.comate.action

import org.archguard.comate.command.ComateContext
import org.archguard.comate.strategy.BasicPromptStrategy
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.nio.file.Path

class DesignSystemPrompterTest {
    @Test
    fun should_generate_correct_prompt_test() {
        val comateContext =
            ComateContext(Path.of("."), "kotlin", null, extArgs = mutableMapOf("actionInput" to "咖啡外卖"))
        val prompter = DesignSystemPrompter(comateContext, BasicPromptStrategy())
        val output = prompter.execute()
        assertEquals(
            output,
            """You're an Architecture,根据详细分析如下的需求信息，设计完整的端到端需求用例。Here is requirements: 1. 请按如下的 DSL 格式返回，不做解释。

需求信息 如下: 

###
咖啡外卖
###
    
            
DSL 格式如下:
```kotlin
caseflow("MovieTicketBooking", defaultRole = "User") {
    activity("AccountManage") {
        task("UserRegistration") {
            stories = listOf("Register with email","Register with phone")
        }
        task("UserLogin") {
            stories = listOf("Login to the website")
        }
    }
}
```
            
"""
        )
    }
}
