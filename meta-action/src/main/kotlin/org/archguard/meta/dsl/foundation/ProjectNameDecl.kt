package org.archguard.meta.dsl.foundation

import org.archguard.meta.base.AtomicRule
import org.archguard.meta.base.PatternWithExampleRule

class ProjectNameDecl : PatternWithExampleRule<String>, BaseDeclaration {
    private var ruleRegex: Regex? = null
    private var sample = ""

    override fun pattern(regex: String) {
        this.ruleRegex = Regex(regex)
    }

    override fun example(sample: String) {
        this.sample = sample
    }

    override fun exec(input: String): Boolean {
        if (ruleRegex != null) {
            val matchResult = ruleRegex!!.find(input)
            return matchResult != null
        }

        return true
    }

    override fun rules(): List<AtomicRule> {
        return listOf()
    }
}