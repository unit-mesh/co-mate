package org.archguard.comate.cli

import org.archguard.comate.action.ComateCommand
import org.archguard.comate.smart.Semantic

fun createEmbeddingMap(create: Semantic): Map<ComateCommand, List<FloatArray>> {
    val basicIntroCommand = listOf(
        "introduction system",
        "介绍一下这个系统",
        "介绍这个系统",
        "介绍系统",
    )
    val archStyleCommand = listOf(
        "layered style",
        "what is layered style",
        "分析该系统的分层",
    )
    val apiGovernanceCommand = listOf(
        "api governance",
        "治理 API ",
        "检查 API 规范",
    )
    val apiGenCommand = listOf(
        "api generate",
        "生成 API",
    )

    return mapOf(
        ComateCommand.Intro to basicIntroCommand.map { create.embed(it) },
        ComateCommand.LayeredStyle to archStyleCommand.map { create.embed(it) },
        ComateCommand.ApiGovernance to apiGovernanceCommand.map { create.embed(it) },
        ComateCommand.ApiGen to apiGenCommand.map { create.embed(it) },
    )
}