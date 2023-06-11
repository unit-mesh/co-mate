package org.archguard.spec.markdown

import org.commonmark.ext.gfm.tables.TableCell
import org.commonmark.ext.gfm.tables.TableHead
import org.commonmark.ext.gfm.tables.TableRow
import org.commonmark.node.AbstractVisitor
import org.commonmark.node.CustomBlock
import org.commonmark.node.CustomNode
import org.commonmark.node.Text

internal class TableToMapVisitor : AbstractVisitor() {
    val headers = mutableMapOf<String, List<String>>()
    private var isBeforeHeadLine = true
    private var headerIndex = 0

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
                } else {
                    val header = headers.keys.elementAt(headerIndex)
                    headers[header] = headers[header]!! + (customNode.firstChild as Text).literal
                }

                headerIndex++
            }

            is TableRow -> {
                headerIndex = 0
            }
        }
    }

    override fun visit(customBlock: CustomBlock?) {
        super.visit(customBlock)
    }
}