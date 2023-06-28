package org.archguard.spec.lang

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class FeatureSpecTest {
    private val assetManagement = FeatureSuite("AssetsManagement") {
        Feature("", tag = "") {
            Scenario("") {
                Given("")
                And("")
                When("")
                Then("")
            }
        }
    }

    @Test
    fun testFeatureSpec() {
        assetManagement.exec("")
    }
}