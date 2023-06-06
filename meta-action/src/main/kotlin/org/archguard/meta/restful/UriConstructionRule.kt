package org.archguard.meta.restful

import org.archguard.meta.ApiRule
import org.archguard.meta.Element

class UriConstructionRule : ApiRule("uri-construction") {
    var ruleRegex: Regex? = null
    var sample = ""

    fun rule(regex: String) {
        this.ruleRegex = Regex(regex)
    }

    fun sample(sample: String) {
        this.sample = sample
    }

    override fun exec(input: Element): Any {
        println("exec: ${this.name}")
        return ""
    }
}

