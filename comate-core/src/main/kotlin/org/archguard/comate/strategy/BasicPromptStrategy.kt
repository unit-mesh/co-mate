package org.archguard.comate.strategy

import org.archguard.comate.action.base.BaseTemplate

class BasicPromptStrategy : Strategy {
    override fun prompt(template: BaseTemplate): String {
        var output = ""

        if (template.getRole().isNotEmpty()) {
            output += "You're an ${template.getRole()},"
        }

        if (template.getInstruction().isNotEmpty()) {
            output += template.getInstruction()
        }

        if (template.getRequirements().isNotEmpty()) {
            output += "Here is requirements: ${template.getRequirements()}\n"
        }

        if (template.getSample().isNotEmpty()) {
            output += "${template.getSample()}\n"
        }

        if (template.getExtendData().isNotEmpty()) {
            output += "${template.getExtendData()}\n"
        }

        return output
    }
}
