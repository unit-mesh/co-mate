package org.archguard.comate.action

abstract class Prompter : BaseTemplate {
    open fun prompt(): String {
        var output = "";

        if (this.getRole().isNotEmpty()) {
            output += "You're an ${this.getRole()}. "
        }

        if (this.getInstruction().isNotEmpty()) {
            output += "${this.getInstruction()}. "
        }

        if (this.getRequirements().isNotEmpty()) {
            output += "Here is requirements: ${this.getRequirements()}. "
        }

        if (this.getSample().isNotEmpty()) {
            output += "${this.getSample()}. "
        }

        if (this.getExtendData().isNotEmpty()) {
            output += "${this.getExtendData()}. "
        }

        return output
    }
}
