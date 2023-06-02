package org.archguard.comate.strategy

import org.archguard.comate.action.BaseTemplate

class BasicPromptStrategy : Strategy {
    override fun prompt(template: BaseTemplate): String {
        var output = "";

        if (template.getRole().isNotEmpty()) {
            output += "You're an ${template.getRole()}. "
        }

        if (template.getInstruction().isNotEmpty()) {
            output += "${template.getInstruction()}. "
        }

        if (template.getRequirements().isNotEmpty()) {
            output += "Here is requirements: ${template.getRequirements()}. "
        }

        if (template.getSample().isNotEmpty()) {
            output += "${template.getSample()}. "
        }

        if (template.getExtendData().isNotEmpty()) {
            output += "${template.getExtendData()}. "
        }

        return output
    }
}
