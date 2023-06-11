package org.archguard.spec.markdown

import org.commonmark.ext.gfm.tables.TableCell
import org.commonmark.ext.gfm.tables.TableHead
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

            val text = TextContentRenderer.builder()
                .nodeRendererFactory(CustomRenderFactory())
                .build().render(node)

            return contentBlocks
        }

        fun tableToHashMap(markdown: String?): Map<String, List<String>> {
            val node: Node = parser.parse(markdown)

            val visitor = TableHeaderVisitor()
            node.accept(visitor)

            return visitor.headers
        }


        private fun parseMarkdownCodeBlock(markdown: String): List<String> {
            val node = parser.parse(markdown)
            val visitor = CodeFilter(lang = "markdown")
            node.accept(visitor)
            return visitor.code
        }
    }
}

internal class TableHeaderVisitor : AbstractVisitor() {
    val headers = mutableMapOf<String, List<String>>()
    private var isBeforeHeadLine = true

    override fun visit(customNode: CustomNode?) {
        super.visit(customNode)

        when (customNode) {
            is TableHead -> {
                isBeforeHeadLine = false
            }

            is TableCell -> {
                if (isBeforeHeadLine) {
                    val header = (customNode.firstChild as Text).literal
                    headers[header] = listOf()
                }
            }
        }
    }

    override fun visit(customBlock: CustomBlock?) {
        super.visit(customBlock)
    }
}

internal class CodeFilter(val lang: String) : AbstractVisitor() {
    var code = listOf<String>()

    override fun visit(fencedCodeBlock: FencedCodeBlock?) {
        if (fencedCodeBlock?.literal != null) {
            if (fencedCodeBlock.info == lang) {
                this.code += fencedCodeBlock.literal
            }
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
