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

fun domain(init: DomainSpec.() -> Unit): DomainSpec {
    val spec = DomainSpec()
    spec.init()
    return spec
}
