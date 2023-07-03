package org.archguard.spec.lang.caseflow.model

import kotlinx.serialization.Serializable

@Serializable
data class CaseFlow(
    val name: String,
    val defaultActor: String = "User",
    val activities: List<Activity>,
    val stories: List<Story>
)

@Serializable
data class Activity(
    val name: String,
    val tasks: List<Task>
)

@Serializable
data class Task(
    val name: String,
    var actor: String? = null,
    var story: List<String> = listOf()
)

@Serializable
data class Story(
    val name: String,
    val scenes: List<Scene>
)

@Serializable
data class Scene(
    val name: String,
    val steps: List<String>
)