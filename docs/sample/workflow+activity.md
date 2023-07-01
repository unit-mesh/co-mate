```
workflow "订单处理" {
  activity "创建订单" {
    onEvent "用户提交订单" {
      execute "创建订单"
    }
  }

  activity "支付订单" {
    onEvent "用户支付订单" {
      execute "支付订单"
    }
    onCondition "订单金额 > 1000" {
      execute "审批订单"
    }
  }

  activity "发货" {
    onEvent "订单支付成功" {
      execute "发货"
    }
  }
}

user "普通用户" {
  perform "提交订单"
  perform "支付订单"
}

user "管理员" {
  perform "审批订单"
}
```


DSL 2:

```kotlin
class WorkflowDSL {
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

    inner class Step(val name: String) {
        lateinit var activity: Activity
        lateinit var next: Step

        fun activity(name: String, init: Activity.() -> Unit) {
            val activity = Activity(name)
            activity.init()
            this.activity = activity
        }

        fun next(step: Step) {
            next = step
        }
    }

    inner class Activity(val name: String) {
        lateinit var role: String
        lateinit var task: String
        lateinit var action: String
    }
}

fun workflow(name: String, init: WorkflowDSL.() -> Unit): WorkflowDSL {
    val workflow = WorkflowDSL()
    workflow.init()
    workflow.build()
    return workflow
}

// Usage example
val myWorkflow = workflow("MyWorkflow") {
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
    // Define other steps and activities here
}

// Accessing workflow elements
val startStep = myWorkflow.start
val endStep = myWorkflow.end
val steps = myWorkflow.steps
```
