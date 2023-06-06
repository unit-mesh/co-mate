package org.archguard.spec.markdown

import org.commonmark.node.*
import org.commonmark.parser.Parser


class ContentBlocking(val subTitle: String, val content: String)

class MarkdownParser {
    fun parseAndOrganizeByTitle(markdown: String?): List<ContentBlocking> {
        val contentBlocks: MutableList<ContentBlocking> = ArrayList()
        val parser = Parser.builder().build()
        val document: Node = parser.parse(markdown)
        val contentBuilder = StringBuilder()
        var currentTitle = ""
        var node: Node = document.firstChild

        while (true) {
            if (node is Heading) {
                if (currentTitle.isNotEmpty()) {
                    contentBlocks.add(ContentBlocking(currentTitle, contentBuilder.toString()))
                    contentBuilder.setLength(0)
                }

                currentTitle = (node.firstChild as Text).literal.toString()
            } else {
                when (node) {
                    is Text -> {
                        contentBuilder.append(node.literal)
                    }

                    else -> {
                        contentBuilder.append(node.toString())
                    }
                }
            }

            if (node.next == null) {
                break
            }

            node = node.next
        }

        if (currentTitle.isNotEmpty()) {
            contentBlocks.add(ContentBlocking(currentTitle, contentBuilder.toString()))
        }

        return contentBlocks
    }
}
