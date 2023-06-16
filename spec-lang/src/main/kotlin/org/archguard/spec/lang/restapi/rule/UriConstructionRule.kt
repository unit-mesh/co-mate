package org.archguard.spec.lang.restapi.rule

import org.archguard.spec.lang.base.PatternWithExampleRule
import org.archguard.spec.base.RuleResult
import org.archguard.spec.lang.restapi.ApiAtomicRule
import org.archguard.spec.element.RestApiElement

class UriConstructionRule : ApiAtomicRule("uri-construction", "uri construction regex: //TODO"),
    PatternWithExampleRule<RestApiElement> {
    private var ruleRegex: Regex? = null
    private var sample = ""

    override fun pattern(regex: String) {
        this.ruleRegex = Regex(regex)
        this.rule = "uri construction regex: $regex"
    }

    override fun example(sample: String) {
        this.sample = sample
    }

    override fun exec(input: RestApiElement): List<RuleResult> {
        if (ruleRegex != null) {
            val matchResult = ruleRegex!!.find(input.uri)
            return listOf(RuleResult(this.actionName, sample, matchResult != null))
        }

        return listOf(RuleResult(this.actionName, sample, false))
    }
}

