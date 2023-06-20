package org.archguard.comate.dynamic.functions

import org.archguard.action.checkout.GitCommandManager
import org.archguard.action.checkout.GitSourceSettings
import org.archguard.action.checkout.helper.GitAuthHelper
import org.archguard.action.checkout.helper.RefHelper
import org.archguard.action.checkout.logger
import org.archguard.action.io.FileExt
import org.archguard.comate.command.ComateContext
import org.archguard.comate.command.fakeComateContext
import java.io.File
import kotlin.io.path.Path

@ComateFunction
class InitializeSystem(override val context: ComateContext = fakeComateContext()) : DyFunction {
    override val hidden: Boolean get() = true

    override fun explain(): String {
        return "Initialize system will clone the repository and setup it."
    }

    override fun execute(): Boolean {
        val settings = GitSourceSettings(context.projectRepo)
        executeGitCheckout(settings)
        context.workdir = Path(settings.repositoryPath)
        return true;
    }

    override fun parameters(): HashMap<String, String> {
        return hashMapOf()
    }
}


// todo: release CodeDB checkout to new version for comate
fun executeGitCheckout(settings: GitSourceSettings) {
    val git = GitCommandManager(settings.repositoryPath)

    val authHelper = GitAuthHelper(git, settings)

    if (File(settings.repositoryPath).exists()) {
        FileExt.rmdir(settings.repositoryPath)
    }

    FileExt.mkdir(settings.repositoryPath)

    authHelper.configureTempGlobalConfig()

    git.config("safe.directory", settings.repositoryPath, true, true)

    logger.info("Initializing git repository")
    git.init()
    git.remoteAdd("origin", settings.repository)

    if (settings.authToken.isNotEmpty()) {
        logger.info("Configuring auth")
        authHelper.configureAuth()
    }

    logger.info("Disabling automatic garbage collection")
    git.tryDisableAutomaticGarbageCollection()

    settings.ref = git.getDefaultBranch(settings.repository)
    logger.info("Determining default branch for repository: ${settings.repository}, default branch: ${settings.ref}")

    val refHelper = RefHelper()
    val refSpec = refHelper.getRefSpecForAllHistory(settings.ref, settings.branch)

    logger.info("Fetching all history for {}", refSpec)
    git.fetch(refSpec)

    val checkoutInfo = refHelper.getCheckoutInfo(git, settings.ref, settings.commit)
    git.checkout(checkoutInfo.ref, checkoutInfo.startPoint)

    // submodules
    if (settings.submodule) {
        git.submoduleSync(settings.nestedSubmodules)
        git.submoduleUpdate(settings.fetchDepth, settings.nestedSubmodules)
        git.submoduleForeach("git config --local gc.auto 0", settings.nestedSubmodules)
    }

    // clean up
    authHelper.removeGlobalConfig()
}