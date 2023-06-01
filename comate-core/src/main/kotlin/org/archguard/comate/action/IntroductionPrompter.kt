package org.archguard.comate.action

import org.archguard.architecture.layered.ChannelType
import org.archguard.comate.context.ComateScaContext
import org.archguard.comate.document.ReadmeParser
import org.archguard.scanner.analyser.ScaAnalyser
import java.nio.file.Path
import kotlin.io.path.Path
import kotlin.io.path.exists
import kotlin.io.path.readText
import kotlin.io.path.relativeTo

class IntroductionPrompter(private val workdir: Path) : Prompter() {
    override fun getRole(): String = "Architecture"
    override fun getInstruction(): String = "根据分析如下的 dependencies 等信息，分析并编写这个项目的介绍。"
    override fun getRequirements(): String = """1. 只返回最可能的 channel type，不做解释。
2. 根据 dependencies 分析这个应用的核心场景。
3. 根据 dependencies 分析这个应用要考虑的功能需求和非功能需求。
4. 你返回的介绍类似于："""

    override fun getSample(): String {
        return """
```
{xxx} 项目是一个 {channel type} 应用程序，使用了 Jetpack Compose、{xxx} 和一系列相关的库来构建 {xxx} 等功能。
该应用还使用了一些第三方库来构建用户界面 {xxx}，以及进行 {xxx} 等任务。该应用需要考虑 {xxx} 等非功能需求。
```""".trimIndent()
    }


    override fun getExtendData(): String {
        val dep = ScaAnalyser(ComateScaContext.create(workdir.toString(), "kotlin")).analyse()
        val depMap: Map<String, List<String>> = dep.groupBy {
            val relativePath = Path(it.path).relativeTo(workdir).toString()
            relativePath
        }.mapValues { entry ->
            entry.value.map { it.depName }
                .toSet()
                .filter { it.isNotEmpty() && it != ":" }
        }

        var instr = "";
        val readmeFile = Path(workdir.toString(), "README.md")
        if (readmeFile.exists()) {
            val readme = readmeFile.readText()
            val readmeParser = ReadmeParser(readme)
            val introduction = readmeParser.introduction()
            instr = "\nProject Instruction: ${introduction.content}\n"
        }

        val items = depMap.map { "| ${it.key} | ${it.value.joinToString(", ")} |" }.joinToString("\n")
        val channels = ChannelType.allValues()
        return instr + """            
dependencies: 

| path | deps |
| --- | --- |
$items

all channel types: $channels
"""
    }
}