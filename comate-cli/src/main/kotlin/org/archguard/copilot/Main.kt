package org.archguard.copilot

import chapi.domain.core.CodeDataStruct
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.archguard.architecture.layered.ChannelType
import org.archguard.rule.core.Issue
import org.archguard.scanner.analyser.ScaAnalyser
import org.archguard.scanner.core.client.ArchGuardClient
import org.archguard.scanner.core.diffchanges.ChangedCall
import org.archguard.scanner.core.estimate.LanguageEstimate
import org.archguard.scanner.core.git.GitLogs
import org.archguard.scanner.core.sca.CompositionDependency
import org.archguard.scanner.core.sca.ScaContext
import org.archguard.scanner.core.sourcecode.CodeDatabaseRelation
import org.archguard.scanner.core.sourcecode.ContainerService
import java.io.File

fun main() {
    val dep = ScaAnalyser(ComateScaContext.create(".", "kotlin")).analyse()
    val depNames = dep
        .map { it.depName }
        .toSet()
        .filter { it.isNotEmpty() && it != ":" }

    val channels = ChannelType.allValues()

    println("""根据下面的 dependencies 信息，编写这个项目的介绍。要求如下：

1. 只返回最可能的 channel type，不做解释。
2. 根据 dependencies 分析这个应用要考虑的非功能需求。
3. 根据 dependencies 分析这个应用使用的三个核心库。
4. 你返回的介绍类似于：

```
这个项目是一个 {channel type} 应用程序，使用了 {xxx} 和一系列相关的库来构建 {xxx} 等功能。该应用还使用了一些第三方库来显 {xxx}，以及进行 {xxx} 等任务。该应用需要考虑 {xxx} 等非功能需求。
```

dependencies: $depNames

all channel types: $channels


    """.trimMargin())
}

class ComateScaContext(
    override val client: ArchGuardClient,
    override val path: String,
    override val language: String,
) : ScaContext {
    companion object {
        fun create(path: String, language: String): ScaContext {
            val client = ComateArchGuardClient()
            return ComateScaContext(client, path, language)
        }
    }
}

class ComateArchGuardClient : ArchGuardClient {
    override fun saveApi(apis: List<ContainerService>) {
        TODO("Not yet implemented")
    }

    override fun saveDataStructure(codes: List<CodeDataStruct>) {
        TODO("Not yet implemented")
    }

    override fun saveDependencies(dependencies: List<CompositionDependency>) {
        // save to json file
        File("dependencies.json").writeText(Json.encodeToString(dependencies))
    }

    override fun saveDiffs(calls: List<ChangedCall>) {
        TODO("Not yet implemented")
    }

    override fun saveEstimates(estimates: List<LanguageEstimate>) {
        TODO("Not yet implemented")
    }

    override fun saveGitLogs(gitLogs: List<GitLogs>) {
        TODO("Not yet implemented")
    }

    override fun saveRelation(records: List<CodeDatabaseRelation>) {
        TODO("Not yet implemented")
    }

    override fun saveRuleIssues(issues: List<Issue>) {
        TODO("Not yet implemented")
    }

}