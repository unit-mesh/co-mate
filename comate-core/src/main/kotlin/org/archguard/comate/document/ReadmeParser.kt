package org.archguard.comate.document

import org.commonmark.node.*
import org.commonmark.parser.Parser
import java.nio.file.Path
import kotlin.io.path.Path
import kotlin.io.path.exists
import kotlin.io.path.readText

data class ReadmeIntroduction(val title: String, val content: String)

class ReadmeParser(content: String) {
    private val parser: Parser = Parser.builder().build()
    private val node: Node = parser.parse(content)

    fun introduction(): ReadmeIntroduction {
        val visitor = IntroductionVisitor()
        node.accept(visitor)
        return visitor.introduction()
    }

    companion object {
        fun introduction(workdir: Path): String {
            var instr = "";
            val readmeFile = Path(workdir.toString(), "README.md")
            if (readmeFile.exists()) {
                val readme = readmeFile.readText()
                val readmeParser = ReadmeParser(readme)
                val introduction = readmeParser.introduction()
                instr = "\nProject introduction: ${introduction.content}\n"
            }

            return instr
        }
    }
}
