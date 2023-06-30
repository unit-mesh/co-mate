package org.archguard.comate.action

import org.archguard.comate.command.ComateContext
import org.archguard.comate.strategy.BasicPromptStrategy
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.nio.file.Path

class ApiGovernancePrompterTest {
    @Test
    fun should_generate_correct_prompt_test() {
        val prompter = ApiGovernancePrompter(ComateContext(Path.of("."), "kotlin", null), BasicPromptStrategy())

        val output = prompter.execute()
        assertEquals("""You're an Architecture Governance Expert,根据下面的信息，总结 RESTful API 的规范情况。Here is requirements: 
1. API 应该符合基本 RESTful API 的规范，如 URI 构造、采用标准的 HTTP 方法、状态码、安全等。
2. 如果 result 是 true，请不要返回任何信息。
3. 如果 result 是 false，请返回不通过的原因，并根据 rule 提供符合规范的 API。
4. 你只返回如下的结果：

###
- API `{api uri}` 不符合 { rule name } 规范，Rule: { rule }，建议 API 修改为 {new api}。
###

results: 
""", output)
    }
}
