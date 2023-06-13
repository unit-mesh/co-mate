package org.archguard.meta.dsl.foundation

import org.archguard.meta.base.AtomicAction
import org.archguard.meta.base.PatternWithExampleRule
import org.archguard.meta.base.RuleResult
import org.archguard.meta.model.FoundationElement

class ProjectNameDeclaration : PatternWithExampleRule<FoundationElement>, BaseDeclaration<FoundationElement> {
    override val actionName = "ProjectName"
    private var originRegex: String = ""
    private var ruleRegex: Regex? = null

    private var sample = ""

    override fun pattern(regex: String) {
        this.originRegex = regex
        try {
            this.ruleRegex = Regex(regex)
        } catch (e: Exception) {
            throw Exception("Invalid regex: $regex")
        }
    }

    override fun example(sample: String) {
        this.sample = sample
    }

    override fun exec(input: FoundationElement): List<RuleResult> {
        val ruleExplain = "regex: " + this.originRegex + "sample: " + sample

        if (ruleRegex != null) {
            val matchResult = ruleRegex!!.find(input.projectName)
            return listOf(RuleResult(this.actionName, ruleExplain, matchResult != null))
        }

        return listOf(RuleResult(this.actionName, ruleExplain, false))
    }

    override fun rules(): List<AtomicAction<FoundationElement>> {
        return listOf(this)
    }
}