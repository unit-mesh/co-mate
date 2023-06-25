package org.archguard.spec.lang.foundation.rule

import chapi.domain.core.CodeDataStruct
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import org.archguard.spec.element.FoundationElement
import org.junit.jupiter.api.Test

class DependencyRuleTest {
    @Test
    fun should_correct_setup_for_deps() {
        val rule = DependencyRule()
        rule.rules = hashMapOf(
            "application" to listOf("domain", "interface"),
            "domain" to listOf("infrastructure"),
            "interface" to listOf("domain")
        )

        // load the ds from the test resources
        val dsString = this.javaClass.classLoader.getResource("spec/ddd-mono-repo-demo.json")!!.readText()
        val ds: List<CodeDataStruct> = Json.decodeFromString(dsString)

        val results = rule.exec(FoundationElement("ddd-mono-repo-demo", ds))
        assert(results.isEmpty())
    }
}
