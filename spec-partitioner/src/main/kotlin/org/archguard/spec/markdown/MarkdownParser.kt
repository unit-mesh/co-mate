package org.archguard.spec.markdown

import org.commonmark.node.*
import org.commonmark.parser.Parser
import org.commonmark.renderer.NodeRenderer
import org.commonmark.renderer.text.CoreTextContentNodeRenderer
import org.commonmark.renderer.text.TextContentNodeRendererContext
import org.commonmark.renderer.text.TextContentNodeRendererFactory
import org.commonmark.renderer.text.TextContentRenderer
import java.util.*


class ContentBlocking(val subTitle: String, val content: String)

class MarkdownParser {
    companion object {
        fun parseAndOrganizeByTitle(markdown: String?): List<ContentBlocking> {
            val contentBlocks: MutableList<ContentBlocking> = ArrayList()
            val parser = Parser.builder().build()
            val node: Node = parser.parse(markdown)

            val text = TextContentRenderer.builder()
                .nodeRendererFactory(CustomRenderFactory())
                .build().render(node)

            return contentBlocks
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
