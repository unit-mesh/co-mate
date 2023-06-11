package org.archguard.spec.markdown

import org.commonmark.node.AbstractVisitor
import org.commonmark.node.FencedCodeBlock

internal class CodeFilter(private val lang: String? = null) : AbstractVisitor() {
    var code = listOf<String>()

    override fun visit(fencedCodeBlock: FencedCodeBlock?) {
        if (fencedCodeBlock?.literal != null) {
            if (lang == null) {
                this.code += fencedCodeBlock.literal
            } else {
                if (fencedCodeBlock.info == lang) {
                    this.code += fencedCodeBlock.literal
                }
            }
        }
    }
}