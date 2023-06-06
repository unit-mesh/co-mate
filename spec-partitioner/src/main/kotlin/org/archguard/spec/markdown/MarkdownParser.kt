package org.archguard.spec.markdown

import org.commonmark.Extension
import org.commonmark.internal.renderer.NodeRendererMap
import org.commonmark.node.Node
import org.commonmark.parser.Parser
import org.commonmark.renderer.Renderer
import org.commonmark.renderer.text.*


class ContentBlocking(val subTitle: String, val content: String)

class MarkdownParser {
    fun parseAndOrganizeByTitle(markdown: String?): List<ContentBlocking> {
        val contentBlocks: MutableList<ContentBlocking> = ArrayList()
        val parser = Parser.builder().build()
        val node: Node = parser.parse(markdown)

        val text = CustomContentRenderer.builder().build().render(node)
        println(text)
        return contentBlocks
    }
}

class CustomContentRenderer private constructor(builder: Builder) : Renderer {
    private val stripNewlines: Boolean
    private val nodeRendererFactories: MutableList<TextContentNodeRendererFactory>

    init {
        stripNewlines = builder.stripNewlines
        nodeRendererFactories = java.util.ArrayList(builder.nodeRendererFactories.size + 1)
        nodeRendererFactories.addAll(builder.nodeRendererFactories)
        // Add as last. This means clients can override the rendering of core nodes if they want.
        nodeRendererFactories.add(TextContentNodeRendererFactory { context -> CoreTextContentNodeRenderer(context) })
    }

    override fun render(node: Node, output: Appendable) {
        val context =
            RendererContext(TextContentWriter(output))
        context.render(node)
    }

    override fun render(node: Node): String {
        val sb = StringBuilder()
        render(node, sb)
        return sb.toString()
    }

    class Builder {
        var stripNewlines = false
        val nodeRendererFactories: MutableList<TextContentNodeRendererFactory> = java.util.ArrayList()

        fun build(): CustomContentRenderer {
            return CustomContentRenderer(this)
        }

        fun stripNewlines(stripNewlines: Boolean): Builder {
            this.stripNewlines = stripNewlines
            return this
        }

        fun nodeRendererFactory(nodeRendererFactory: TextContentNodeRendererFactory): Builder {
            nodeRendererFactories.add(nodeRendererFactory)
            return this
        }

        fun extensions(extensions: Iterable<Extension?>): Builder {
            for (extension in extensions) {
                if (extension is TextContentRendererExtension) {
                    extension.extend(this)
                }
            }
            return this
        }
    }

    interface TextContentRendererExtension : Extension {
        fun extend(rendererBuilder: Builder?)
    }

    private inner class RendererContext(private val textContentWriter: TextContentWriter) :
        TextContentNodeRendererContext {
        private val nodeRendererMap = NodeRendererMap()

        init {
            // The first node renderer for a node type "wins".
            for (i in nodeRendererFactories.indices.reversed()) {
                val nodeRendererFactory = nodeRendererFactories[i]
                val nodeRenderer = nodeRendererFactory.create(this)
                nodeRendererMap.add(nodeRenderer)
            }
        }

        override fun stripNewlines(): Boolean {
            return stripNewlines
        }

        override fun getWriter(): TextContentWriter {
            return textContentWriter
        }

        override fun render(node: Node) {
            nodeRendererMap.render(node)
        }
    }

    companion object {
        fun builder(): Builder {
            return Builder()
        }
    }
}
