package org.archguard.comate.action

import org.archguard.comate.command.ComateContext
import org.archguard.comate.governance.ApiRuleVerifier
import org.archguard.comate.strategy.CodePromptStrategy
import org.archguard.comate.strategy.Strategy
import org.archguard.spec.element.FoundationElement
import org.archguard.spec.lang.foundation
import org.archguard.spec.lang.matcher.shouldBe
import org.archguard.spec.lang.matcher.shouldNotBe

class FoundationGovernancePrompter(
    val context: ComateContext,
    override val strategy: Strategy,
) : CodePromptStrategy {
    override fun getRole(): String = "Architecture"
    override fun getInstruction(): String = "根据下面的信息，总结项目的基础规范实施情况。"
    override fun getRequirements(): String = """
1. 使用中文描述。
2. 使用业务场景的语言描述，不要使用技术术语。

    """.trimIndent()

    override fun getExtendData(): String {
        val codeDataStructs = context.fetchDs()

        val governance = foundation {
            project_name {
                pattern("^([a-z0-9-]+)-([a-z0-9-]+)-([a-z0-9-]+)(-common)?\${'$'}")
                example("system1-servicecenter1-microservice1")
            }

            layered {
                layer("application") {
                    pattern(".*\\.application") { name shouldBe endWiths("DTO", "Request", "Response") }
                }
                layer("domain") {
                    pattern(".*\\.domain") { name shouldBe endWiths("Entity") }
                }
                layer("infrastructure") {
                    pattern(".*\\.infrastructure") { name shouldBe endWiths("Repository", "Mapper") }
                }
                layer("interface") {
                    pattern(".*\\.interface") { name shouldBe endWiths("Controller", "Service") }
                }

                dependency {
                    "application" dependedOn "domain"
                    "application" dependedOn "interface"
                    "domain" dependedOn "infrastructure"
                    "interface" dependedOn "domain"
                }
            }

            naming {
                class_level {
                    style("CamelCase")
                    pattern(".*") { name shouldNotBe contains("${'$'}") }
                }
                function_level {
                    style("CamelCase")
                    pattern(".*") { name shouldNotBe contains("${'$'}") }
                }
            }
        }

        governance.setVerifier(ApiRuleVerifier(context.connector!!))
        val ruleResults = governance.exec(FoundationElement(context.projectName, codeDataStructs))

        val introduction = context.fetchReadmeIntroduction()
        return """$introduction

rule governance results: $ruleResults

""".trimIndent()
    }
}