package org.archguard.comate.wrapper

import chapi.domain.core.CodeDataStruct
import org.archguard.rule.core.Issue
import org.archguard.scanner.core.client.ArchGuardClient
import org.archguard.scanner.core.diffchanges.ChangedCall
import org.archguard.scanner.core.estimate.LanguageEstimate
import org.archguard.scanner.core.git.GitLogs
import org.archguard.scanner.core.sca.CompositionDependency
import org.archguard.scanner.core.sourcecode.CodeDatabaseRelation
import org.archguard.scanner.core.sourcecode.ContainerService

class ComateArchGuardClient : ArchGuardClient {
    var apis = mutableListOf<ContainerService>()
    override fun saveApi(apis: List<ContainerService>) {
        this.apis = apis.toMutableList()
    }

    override fun saveDataStructure(codes: List<CodeDataStruct>) = Unit
    override fun saveDependencies(dependencies: List<CompositionDependency>) = Unit
    override fun saveDiffs(calls: List<ChangedCall>) = Unit
    override fun saveEstimates(estimates: List<LanguageEstimate>) = Unit
    override fun saveGitLogs(gitLogs: List<GitLogs>) = Unit
    override fun saveRelation(records: List<CodeDatabaseRelation>) = Unit
    override fun saveRuleIssues(issues: List<Issue>) = Unit
}