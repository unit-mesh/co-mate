package org.archguard.meta.restful.rule

import org.archguard.meta.restful.ApiRule
import org.archguard.meta.restful.RestApi

class SecurityRule(val ruleContent: String) : ApiRule("security") {
    override fun exec(input: RestApi): Boolean {
        return true
    }
}
