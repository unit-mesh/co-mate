package org.archguard.spec.lang

import org.archguard.spec.lang.caseflow.model.CaseFlow
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class CaseFlowSpecTest {
    @Test
    fun default() {
        val spec = CaseFlowSpec.defaultSpec()
        assertEquals(CaseFlowSpec.defaultSpec().toString(), spec.toString())
    }

    @Test
    fun should_convert_story_to_string() {
        val spec = caseflow("story") {
            story("test") {
                scene("test") {
                    Given("given test")
                    And("and something")
                    When("when test")
                    Then("then other thing")
                }
            }
        }

        println(spec.toString())
        assertEquals(spec.toString(), """
            |caseflow("story") {
            |    story("test") {
            |        scene("test") {
            |            Given("given test")
            |            And("and something")
            |            When("when test")
            |            Then("then other thing")
            |        }
            |    }
            |}
        """.trimMargin())
    }

    @Test
    fun should_convert_activity_and_story_to_string() {
        val spec = caseflow("story") {
            activity("test") {
                task("test") {
                    actor = "test"
                    stories = listOf("test")
                }
            }
            story("test2") {
                scene("test2") {
                    Given("given test2")
                    And("and something2")
                    When("when test2")
                    Then("then other thing2")
                }
            }
        }

        assertEquals(spec.toString(), """
            |caseflow("story") {
            |    activity("test") {
            |        task("test") {
            |            actor = "test"
            |            stories = listOf("test")
            |        }
            |    }
            |    story("test2") {
            |        scene("test2") {
            |            Given("given test2")
            |            And("and something2")
            |            When("when test2")
            |            Then("then other thing2")
            |        }
            |    }
            |}
        """.trimMargin())
    }

    @Test
    fun should_success_convert_dsl_to_models() {
        val spec = caseflow("story") {
            story("test") {
                scene("test") {
                    Given("given test")
                    And("and something")
                    When("when test")
                    Then("then other thing")
                }
            }
        }

        val flow: List<CaseFlow> = spec.exec("nothing")
        assertEquals(1, flow.size)
        assertEquals("story", flow[0].name)

        val firstStory = flow[0].stories[0]
        assertEquals("test", firstStory.name)
        assertEquals("test", firstStory.scenes[0].name)
        val steps = firstStory.scenes[0].steps
        assertEquals("given test", steps[0])
    }

    @Test
    fun should_success_convert_dsl_to_models_with_multiple_stories() {
        val spec = caseflow("story") {
            story("test") {
                scene("test") {
                    Given("given test")
                    And("and something")
                    When("when test")
                    Then("then other thing")
                }
            }
            story("test2") {
                scene("test2") {
                    Given("given test2")
                    And("and something2")
                    When("when test2")
                    Then("then other thing2")
                }
            }
        }

        val flow: List<CaseFlow> = spec.exec("nothing")
        assertEquals(1, flow.size)
        assertEquals("story", flow[0].name)

        val firstStory = flow[0].stories[0]
        assertEquals("test", firstStory.name)
        assertEquals("test", firstStory.scenes[0].name)
        val steps = firstStory.scenes[0].steps
        assertEquals("given test", steps[0])

        val secondStory = flow[0].stories[1]
        assertEquals("test2", secondStory.name)
        assertEquals("test2", secondStory.scenes[0].name)
        val secondSteps = secondStory.scenes[0].steps
        assertEquals("given test2", secondSteps[0])
    }

    @Test
    fun should_convert_activity_to_model() {
        val spec = caseflow("story") {
            activity("test") {
                task("test") {
                    actor = "test"
                    stories = listOf("test")
                }
            }
        }

        val flow: List<CaseFlow> = spec.exec("nothing")
        assertEquals(1, flow.size)
        assertEquals("story", flow[0].name)

        val firstActivity = flow[0].activities[0]
        assertEquals("test", firstActivity.name)
        assertEquals("test", firstActivity.tasks[0].name)
        assertEquals("test", firstActivity.tasks[0].actor)
        assertEquals("test", firstActivity.tasks[0].storyNames[0])
    }
}