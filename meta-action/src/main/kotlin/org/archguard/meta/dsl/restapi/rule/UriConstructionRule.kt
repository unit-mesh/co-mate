package org.archguard.meta.dsl.restapi.rule

import org.archguard.meta.base.PatternWithExampleRule
import org.archguard.meta.base.RuleResult
import org.archguard.meta.dsl.restapi.ApiAtomicRule
import org.archguard.meta.model.RestApiElement

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

