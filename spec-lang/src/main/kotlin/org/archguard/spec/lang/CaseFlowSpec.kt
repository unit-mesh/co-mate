package org.archguard.spec.lang

import org.archguard.spec.lang.base.Spec
import org.archguard.spec.lang.caseflow.declaration.StoryDeclaration
import org.archguard.spec.lang.caseflow.Activity
import org.archguard.spec.lang.caseflow.CaseFlow
import org.archguard.spec.lang.caseflow.Story
import org.archguard.spec.lang.caseflow.Task

@SpecDsl
class CaseFlowSpec(val name: String, private val defaultActor: String) : Spec<String> {
    lateinit var start: ActivityDeclaration
    lateinit var end: ActivityDeclaration
    private val activitiesDeclarations = mutableListOf<ActivityDeclaration>()
    private val storyDeclarations = mutableListOf<StoryDeclaration>()
    private val stories = mutableListOf<Story>()

    fun activity(name: String, init: ActivityDeclaration.() -> Unit) {
        val activityDeclaration = ActivityDeclaration(name)
        activityDeclaration.init()
        activitiesDeclarations.add(activityDeclaration)
    }

    fun story(storyName: String, function: StoryDeclaration.() -> Unit): StoryDeclaration {
        val storyDeclaration = StoryDeclaration(storyName)
        storyDeclaration.function()
        stories.add(storyDeclaration.toModel())
        storyDeclarations.add(storyDeclaration)
        return storyDeclaration
    }

    open inner class NamedActivity(open val name: String) {
        override fun toString(): String {
            return "NamedActivity(name='$name')"
        }
    }

    inner class ActivityDeclaration(override val name: String) : NamedActivity(name) {
        var next: NamedActivity? = null
        private val taskDeclarations = mutableListOf<TaskDeclaration>()

        fun task(name: String, init: TaskDeclaration.() -> Unit) {
            val taskDeclaration = TaskDeclaration(name)
            taskDeclaration.init()
            taskDeclarations.add(taskDeclaration)
        }

        fun toModel(): Activity {
            return Activity(name, taskDeclarations.map { it.toModel() })
        }

        override fun toString(): String {
            return """activity("$name") {
                |    ${taskDeclarations.joinToString("\n    ") { "    ${it.toString().replace("\n", "\n    ")}" }}
                |    }
            """.trimMargin()
        }
    }

    inner class TaskDeclaration(val name: String) {
        var actor: String = ""
        var stories: List<String> = listOf()

        fun toModel(): Task {
            return Task(name, actor, stories)
        }

        override fun toString(): String {
            val sb = StringBuilder()
            sb.append("task(\"$name\") {\n")

            if (actor.isNotEmpty()) {
                sb.append("        actor = \"$actor\"\n")
            }
            if (stories.isNotEmpty()) {
                sb.append("        stories = ${stories.joinToString("\",\"", "listOf(\"", "\")")}\n")
            }

            sb.append("    }")
            return sb.toString()
        }
    }

    override fun exec(element: String): List<CaseFlow> {
        return listOf(CaseFlow(name, defaultActor, activitiesDeclarations.map { it.toModel() }, stories))
    }

    override fun default(): Spec<String> = defaultSpec()

    override fun toString(): String {
        val sb = StringBuilder()
        if (defaultActor.isNotEmpty()) {
            sb.append("caseflow(\"$name\", defaultRole = \"$defaultActor\") {\n")
        } else {
            sb.append("caseflow(\"$name\") {\n")
        }

        if (activitiesDeclarations.isNotEmpty()) {
            sb.append("    ${activitiesDeclarations.joinToString("\n    ")}\n")
        }
        if (storyDeclarations.isNotEmpty()) {
            sb.append("    ${storyDeclarations.joinToString("\n    ")}\n")
        }
        sb.append("}")

        return sb.toString()
    }

    companion object {
        fun exampleActivitySpec() : String = """
            caseflow("MovieTicketBooking", defaultActor = "User") {
                activity("AccountManage") {
                    task("UserRegistration") {
                        stories = listOf("Register with email", "Register with phone")
                    }
                    task("UserLogin") {
                        stories += "Login to the website"
                        // actor = "Admin" // if some task is actor-specific, you can specify it here
                    }
                }
            }
            """.trimIndent()

        fun defaultSpec(): CaseFlowSpec =
            // 使用如下的 DSL 编写一个 OKR 协作与管理系统 的需求全景。要求： 1. 你返回的内容格式如下：```kotlin
            caseflow("MovieTicketBooking", defaultActor = "User") {
                // activity's should consider all user activities
                activity("AccountManage") {
                    // task part should include all user tasks under the activity
                    task("UserRegistration") {
                        // you should list key steps in the story
                        stories = listOf("Register with email", "Register with phone")
                    }
                    task("UserLogin") {
                        stories += "Login to the website"
                    }
                }
                activity("MovieSelection") {}
                // ...
                activity("PaymentCancel") {
                    task("ConfirmCancel") {
                        actor = "Admin" // if some task is role-specific, you can specify it here
                        //...
                    }
                }
            }
    }
}

/**
 * CaseFlow DSL would be used to describe the workflow of a business case, like user journey, business process, etc.
 */
fun caseflow(name: String, defaultActor: String = "", init: CaseFlowSpec.() -> Unit): CaseFlowSpec {
    val workflow = CaseFlowSpec(name, defaultActor)
    workflow.init()
    return workflow
}