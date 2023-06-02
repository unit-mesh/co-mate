package org.archguard.comate.document

import org.commonmark.node.*
import org.commonmark.parser.Parser

data class ReadmeIntroduction(val title: String, val content: String)

class ReadmeParser(content: String) {
    private val parser: Parser = Parser.builder().build()
    private val node: Node = parser.parse(content)

    fun introduction(): ReadmeIntroduction {
        val visitor = IntroductionVisitor()
        node.accept(visitor)
        return visitor.introduction()
    }
}

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
