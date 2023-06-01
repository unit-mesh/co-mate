package org.archguard.comate.document

import org.commonmark.node.*
import org.commonmark.parser.Parser


class ReadmeParser(val content: String) {
    val parser: Parser = Parser.builder().build()
    val node: Node = parser.parse(content)

    fun introduction(): String {
        val visitor = IntroductionVisitor()
        node.accept(visitor)
        return visitor.introduction()
    }
}

class IntroductionVisitor : AbstractVisitor() {
    private var paragraphs = mutableListOf<String>()
    private var headers = mutableListOf<String>()

    override fun visit(paragraph: Paragraph?) {
        if (paragraph?.firstChild is Text) {
            paragraphs.add((paragraph.firstChild as Text).literal)
            val text = paragraph.firstChild

            var next = text.next
            while (next != null && (next is Text || next is SoftLineBreak)) {
                if (next is Text) {
                    paragraphs[paragraphs.lastIndex] += next.literal
                }

                val current = next
                next = text.next

                if (current !is Text || next !is SoftLineBreak) {
                    break
                }
            }
        }
    }


    override fun visit(heading: Heading?) {
        if (heading?.firstChild is Text) {
            headers.add((heading.firstChild as Text).literal)
        }
    }

    fun introduction(): String {
        val title = headers.firstOrNull() ?: ""
        val description = paragraphs.joinToString("")

        return """
            # $title
            
            $description
        """.trimIndent()
    }


}
