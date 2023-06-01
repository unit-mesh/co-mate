package org.archguard.copilot

import chapi.domain.core.CodeDataStruct
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

fun main() {
    ScaAnalyser(ComateScaContext.create("comate", ".", "kotlin")).analyse()
}

class ComateScaContext(
    override val client: ArchGuardClient,
    override val path: String,
    override val language: String,
) : ScaContext {
    companion object {
        fun create(id: String, path: String, language: String): ScaContext {
            val client = ComateJsonClient(id)
            return ComateScaContext(client, path, language)
        }
    }
}

class ComateJsonClient(private val systemId: String) : ArchGuardClient {
    override fun saveApi(apis: List<ContainerService>) {
        TODO("Not yet implemented")
    }

    override fun saveDataStructure(codes: List<CodeDataStruct>) {
        TODO("Not yet implemented")
    }

    override fun saveDependencies(dependencies: List<CompositionDependency>) {
        println(dependencies)
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