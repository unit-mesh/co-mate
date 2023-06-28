package org.archguard.spec.lang

import org.archguard.spec.base.verifier.LlmRuleVerifier
import org.archguard.spec.base.RuleResult
import org.archguard.spec.lang.base.Spec
import org.archguard.spec.lang.domain.declaration.ContextMapDeclaration

@SpecDsl
class DomainSpec : Spec<Any> {
    fun context_map(name: String, block: ContextMapDeclaration.() -> Unit): ContextMapDeclaration {
        val contextMapDeclaration = ContextMapDeclaration(name)
        contextMapDeclaration.block()
        return contextMapDeclaration
    }

    override fun setVerifier(ruleVerifier: LlmRuleVerifier) {

    }

    override fun default(): Spec<Any> {
        return defaultSpec()
    }

    override fun exec(element: Any): List<RuleResult> {
        return listOf()
    }

    companion object {
        fun defaultSpec(): DomainSpec {
            return domain {
                context_map("TicketBooking") {
                    context("Reservation") {}
                    context("Ticket") {}

                    mapping {
                        context("Reservation") dependedOn context("Ticket")
                        context("Reservation") dependedOn context("Movie")
                    }
                }
            }
        }
    }
}

class UserStorySpec : Spec<String> {
    override fun default(): Spec<String> {
        return defaultSpec()
    }

    fun as_an(role: String) {

    }

    fun i_want_to(action: String) {

    }

    fun so_that(result: String) {

    }

    companion object {
        fun defaultSpec(): UserStorySpec {
            return user_story {

            }
        }
    }
}

fun domain(init: DomainSpec.() -> Unit): DomainSpec {
    val spec = DomainSpec()
    spec.init()
    return spec
}

fun user_story(init: UserStorySpec.() -> Unit): UserStorySpec {
    val spec = UserStorySpec()
    spec.init()
    return spec
}