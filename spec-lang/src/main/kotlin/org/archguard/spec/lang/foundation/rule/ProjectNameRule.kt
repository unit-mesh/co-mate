package org.archguard.spec.lang.foundation.rule

import org.archguard.spec.base.Rule
import org.archguard.spec.lang.base.BaseDeclaration
import org.archguard.spec.lang.base.PatternWithExampleRule
import org.archguard.spec.base.RuleResult
import org.archguard.spec.element.FoundationElement
import org.jetbrains.annotations.TestOnly

class ProjectNameRule : PatternWithExampleRule<FoundationElement>, BaseDeclaration<FoundationElement> {
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
            return listOf(RuleResult(this.actionName, ruleExplain, matchResult != null, input.projectName))
        }

        return listOf(RuleResult(this.actionName, ruleExplain, false, input.projectName))
    }

    override fun rules(element: FoundationElement): List<Rule<FoundationElement>> {
        return listOf(this)
    }

    override fun toString(): String {
        return """
        project_name {
            pattern("${this.originRegex}")
            example("${this.sample}")
        }
        """.trimIndent()
    }
}

@TestOnly
fun project_name_t(init: ProjectNameRule.() -> Unit): ProjectNameRule {
    val rule = ProjectNameRule()
    rule.init()
    return rule
}
