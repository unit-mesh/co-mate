package org.archguard.spec.lang

import org.archguard.spec.lang.base.Spec

class ConceptSpec : Spec<String> {
    override fun default(): Spec<String> {
        return defaultSpec()
    }

    override fun toString(): String {
        return "ConceptSpec()"
    }

    companion object {
        fun defaultSpec(): ConceptSpec {
            return concept {
//                concepts {}
//                relations {}
            }
        }
    }
}

fun concept(function: ConceptSpec.() -> Unit): ConceptSpec {
    val conceptSpec = ConceptSpec()
    conceptSpec.function()
    return conceptSpec
}
