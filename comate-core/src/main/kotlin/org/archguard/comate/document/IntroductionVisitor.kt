package org.archguard.comate.document

import org.commonmark.node.*

class IntroductionVisitor : AbstractVisitor() {
    private var paragraphs = mutableListOf<String>()
    private var headers = mutableListOf<String>()

    override fun visit(paragraph: Paragraph?) {
        var result = ""
        if (paragraph?.firstChild is Text) {
            result += (paragraph.firstChild as Text).literal
            var next = paragraph.firstChild?.next

            while (next is Text || next is SoftLineBreak) {
                if (next is Text) {
                    result += "\n" + next.literal
                }

                next = next.next
            }

            paragraphs.add(result)
        }
    }

    override fun visit(heading: Heading?) {
        if (heading?.firstChild is Text) {
            headers.add((heading.firstChild as Text).literal)
        }
    }

    fun introduction(): ReadmeIntroduction {
        val title = headers.firstOrNull() ?: ""
        val description = paragraphs.firstOrNull() ?: ""

        return ReadmeIntroduction(title, description)
    }
}
