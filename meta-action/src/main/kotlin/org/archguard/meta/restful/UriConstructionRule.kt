package org.archguard.meta.restful

import org.archguard.meta.ApiRule
import org.archguard.meta.RestApi

class UriConstructionRule : ApiRule("uri-construction") {
    var ruleRegex: Regex? = null
    var sample = ""

    fun rule(regex: String) {
        this.ruleRegex = Regex(regex)
    }

    fun example(sample: String) {
        this.sample = sample
    }

    override fun exec(input: RestApi): Boolean {
        println("exec: ${this.name}")
        if (ruleRegex != null) {
            val matchResult = ruleRegex!!.find(input.uri)
            return matchResult != null
        }

        return true
    }
}

