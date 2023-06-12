package org.archguard.meta.dsl.foundation

import org.archguard.meta.base.AtomicAction
import org.archguard.meta.base.PatternWithExampleRule
import org.archguard.meta.base.RuleResult
import org.archguard.meta.model.FoundationElement

class ProjectNameDecl : PatternWithExampleRule<FoundationElement>, BaseDeclaration<FoundationElement> {
    override val name = "ProjectName"
    private var ruleRegex: Regex? = null
    private var sample = ""

    override fun pattern(regex: String) {
        this.ruleRegex = Regex(regex)
    }

    override fun example(sample: String) {
        this.sample = sample
    }

    override fun exec(input: FoundationElement): RuleResult {
        if (ruleRegex != null) {
            val matchResult = ruleRegex!!.find(input.projectName)
            return RuleResult(this.name, sample, matchResult != null)
        }

        return RuleResult(this.name, sample, false)
    }

    override fun rules(): List<AtomicAction<FoundationElement>> {
        return listOf(this)
    }
}