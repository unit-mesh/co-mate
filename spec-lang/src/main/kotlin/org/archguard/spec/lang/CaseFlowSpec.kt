package org.archguard.spec.lang

import org.archguard.spec.lang.base.Spec

class CaseFlowSpec : Spec<String> {
    lateinit var start: Step
    lateinit var end: Step
    private val steps = mutableListOf<Step>()

    fun step(name: String, init: Step.() -> Unit) {
        val step = Step(name)
        step.init()
        steps.add(step)
    }

    fun build() {
        // Workflow construction logic goes here
    }

    open inner class NamedStep(open val name: String) {
        override fun toString(): String {
            return "NamedStep(name='$name')"
        }
    }

    inner class Step(override val name: String) : NamedStep(name) {
        var activity: Activity = Activity("")
        var next: NamedStep? = null

        fun activity(name: String, init: Activity.() -> Unit) {
            val activity = Activity(name)
            activity.init()
            this.activity = activity
        }

        fun next(step: NamedStep) {
            this.next = step
        }

        override fun toString(): String {
            return "Step(name='$name', activity=$activity, next=$next)"
        }
    }

    inner class Activity(val name: String) {
        var role: String = ""
        var task: String = ""
        var action: String = ""

        override fun toString(): String {
            return "Activity(name='$name', role='$role', task='$task', action='$action')"
        }
    }

    override fun default(): Spec<String> {
        return defaultSpec()
    }

    override fun toString(): String {
        return "CaseFlowSpec(steps=$steps)"
    }

    companion object {
        fun defaultSpec(): CaseFlowSpec {
            return caseflow("MyWorkflow") {
                step("Step1") {
                    activity("UserRegistration") {
                        role = "User"
                        task = "Register on the website"
                        action = "Fill out the registration form"
                    }
                    next(Step("Step2"))
                }
                step("Step2") {
                    activity("ProfileCreation") {
                        role = "User"
                        task = "Create user profile"
                        action = "Provide personal information"
                    }
                    next(Step("Step3"))
                }
            }
        }
    }


}

fun caseflow(name: String, init: CaseFlowSpec.() -> Unit): CaseFlowSpec {
    val workflow = CaseFlowSpec()
    workflow.init()
    workflow.build()
    return workflow
}