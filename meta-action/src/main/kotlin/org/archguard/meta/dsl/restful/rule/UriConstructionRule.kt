package org.archguard.meta.dsl.restful.rule

import org.archguard.meta.base.PatternWithExampleRule
import org.archguard.meta.base.ApiAtomicRule
import org.archguard.meta.dsl.restful.RestApi

class UriConstructionRule : ApiAtomicRule("uri-construction", "uri construction regex: //TODO"), PatternWithExampleRule {
    private var ruleRegex: Regex? = null
    private var sample = ""

    override fun pattern(regex: String) {
        this.ruleRegex = Regex(regex)
        this.rule = "uri construction regex: $regex"
    }

    override fun example(sample: String) {
        this.sample = sample
    }

    override fun exec(input: RestApi): Boolean {
        if (ruleRegex != null) {
            val matchResult = ruleRegex!!.find(input.uri)
            return matchResult != null
        }

        return true
    }
}

