package org.archguard.comate.token

interface TokenCalculate {
    val maxToken: Int
    fun calculate(input: String): Int
}