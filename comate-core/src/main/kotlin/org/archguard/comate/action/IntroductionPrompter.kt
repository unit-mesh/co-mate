package org.archguard.comate.action

import org.archguard.architecture.layered.ChannelType
import org.archguard.comate.context.ComateScaContext
import org.archguard.scanner.analyser.ScaAnalyser
import java.nio.file.Path
import kotlin.io.path.Path
import kotlin.io.path.relativeTo

class IntroductionPrompter(private val workdir: Path) : Prompter {
    override fun prompt(): String {
        val dep = ScaAnalyser(ComateScaContext.create(workdir.toString(), "kotlin")).analyse()
        val depMap: Map<String, List<String>> = dep.groupBy {
            val relativePath = Path(it.path).relativeTo(workdir).toString()
            relativePath
        }.mapValues {
            it.value.map { it.depName }
                .toSet()
                .filter { it.isNotEmpty() && it != ":" }
        }

        val channels = ChannelType.allValues()

        return """根据下面的 dependencies 信息，编写这个项目的介绍。要求如下：

1. 只返回最可能的 channel type，不做解释。
2. 根据 dependencies 分析这个应用的核心场景。
3. 根据 dependencies 分析这个应用要考虑的功能需求和非功能需求。
4. 你返回的介绍类似于：

```
这个项目是一个 {channel type} 应用程序，使用了 Jetpack Compose、{xxx} 和一系列相关的库来构建 {xxx} 等功能。
该应用还使用了一些第三方库来构建用户界面 {xxx}，以及进行 {xxx} 等任务。该应用需要考虑 {xxx} 等非功能需求。
```

dependencies: 

| path | deps |
| --- | --- |
| ${depMap.map { "| ${it.key} | ${it.value.joinToString(", ")} |" }.joinToString("\n")} |

all channel types: $channels


    """.trimMargin()
    }
}