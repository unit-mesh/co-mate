package org.archguard.meta.restful

import org.archguard.meta.ApiRule
import org.archguard.meta.RestApi

class SecurityRule(val ruleContent: String) : ApiRule("security") {
    override fun exec(input: RestApi): Boolean {
        return true
    }
}
