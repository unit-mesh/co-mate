package org.archguard.comate

import chapi.domain.core.CodeDataStruct
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.archguard.rule.core.Issue
import org.archguard.scanner.core.client.ArchGuardClient
import org.archguard.scanner.core.diffchanges.ChangedCall
import org.archguard.scanner.core.estimate.LanguageEstimate
import org.archguard.scanner.core.git.GitLogs
import org.archguard.scanner.core.sca.CompositionDependency
import org.archguard.scanner.core.sourcecode.CodeDatabaseRelation
import org.archguard.scanner.core.sourcecode.ContainerService
import java.io.File

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