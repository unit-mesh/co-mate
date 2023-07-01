package org.archguard.spec.lang

import org.archguard.spec.lang.base.Spec

class CaseFlowSpec(name: String, defaultRole: String) : Spec<String> {
    lateinit var start: Activity
    lateinit var end: Activity
    private val activities = mutableListOf<Activity>()

    fun activity(name: String, init: Activity.() -> Unit) {
        val activity = Activity(name)
        activity.init()
        activities.add(activity)
    }

    fun build() {
        // Workflow construction logic goes here
    }

    open inner class NamedActivity(open val name: String) {
        override fun toString(): String {
            return "NamedStep(name='$name')"
        }
    }

    inner class Activity(override val name: String) : NamedActivity(name) {
        var task: Task = Task("")
        var next: NamedActivity? = null

        fun task(name: String, init: Task.() -> Unit) {
            val task = Task(name)
            task.init()
            this.task = task
        }

        // optional, default to use the next step
        fun next(step: NamedActivity) {
            this.next = step
        }

        override fun toString(): String {
            return "Step(name='$name', activity=$task, next=$next)"
        }
    }

    inner class Task(val name: String) {
        var role: String = ""
        var story: List<String> = listOf()

        override fun toString(): String {
            return "Activity(name='$name', role='$role', story='$story')"
        }
    }

    override fun default(): Spec<String> {
        return defaultSpec()
    }

    override fun toString(): String {
        return "CaseFlowSpec(steps=$activities)"
    }

    companion object {
        fun defaultSpec(): CaseFlowSpec =
            // 使用如下的 DSL 编写一个 OKR 协作与管理系统 的需求全景。要求： 1. 你返回的内容格式如下：```kotlin
            caseflow("MovieTicketBooking", defaultRole = "User") {
                // activity's should consider all user activities
                activity("AccountManage") {
                    // task part should include all user tasks under the activity
                    task("UserRegistration") {
                        // you should list key steps in the story
                        story = listOf("Register with email", "Register with phone")
                    }
                    task("UserLogin") {
                        story += "Login to the website"
                    }
                }
                activity("MovieSelection") {}
                // ...
                activity("PaymentCancel") {
                    task("ConfirmCancel") {
                        role = "Admin" // if some task is role-specific, you can specify it here
                        //...
                    }
                }
            }
    }
}

fun caseflow(name: String, defaultRole: String = "User", init: CaseFlowSpec.() -> Unit): CaseFlowSpec {
    val workflow = CaseFlowSpec(name, defaultRole)
    workflow.init()
    workflow.build()
    return workflow
}