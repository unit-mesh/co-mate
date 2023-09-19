package org.archguard.spec.markdown

import org.commonmark.ext.gfm.tables.TablesExtension
import org.commonmark.node.*
import org.commonmark.parser.Parser
import org.commonmark.renderer.NodeRenderer
import org.commonmark.renderer.text.CoreTextContentNodeRenderer
import org.commonmark.renderer.text.TextContentNodeRendererContext
import org.commonmark.renderer.text.TextContentNodeRendererFactory
import org.commonmark.renderer.text.TextContentRenderer
import java.util.*


class ContentBlocking(val subTitle: String, val content: String)

fun createParser(): Parser {
    return Parser.builder()
        .extensions(listOf(TablesExtension.create()))
        .build()
}

class MarkdownParser {

    companion object {
        private val parser = createParser()

        fun parseAndOrganizeByTitle(markdown: String?): List<ContentBlocking> {
            val contentBlocks: MutableList<ContentBlocking> = ArrayList()
            val node: Node = parser.parse(markdown)

            TextContentRenderer.builder()
                .nodeRendererFactory(CustomRenderFactory())
                .build().render(node)

            return contentBlocks
        }

        fun tableToHashMap(markdown: String?): Map<String, List<String>> {
            val node: Node = parser.parse(markdown)

            val visitor = TableToMapVisitor()
            node.accept(visitor)

            return visitor.headers
        }

        fun parseMarkdownCodeBlock(markdown: String, lang: String? = null): List<String> {
            val node = parser.parse(markdown)
            val visitor = CodeFilter(lang)
            node.accept(visitor)
            return visitor.code
        }
    }
}


class CustomRenderFactory : TextContentNodeRendererFactory {
    override fun create(context: TextContentNodeRendererContext?): NodeRenderer {
        return CustomNodeRenderer(context!!)
    }
}

class CustomNodeRenderer(context: TextContentNodeRendererContext) : CoreTextContentNodeRenderer(context) {
    override fun render(node: Node?) {
        println("rendering node: $node")
        node?.accept(this)
    }
}
