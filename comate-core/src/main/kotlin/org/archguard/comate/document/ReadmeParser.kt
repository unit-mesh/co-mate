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
