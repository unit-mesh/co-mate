package org.archguard.comate.smart

import kotlin.math.sqrt

fun cosineSimilarity(first: FloatArray, second: FloatArray): Float {
    var dotProduct = 0f
    var normA = 0f
    var normB = 0f

    for (i in first.indices) {
        dotProduct += first[i] * second[i]
        normA += first[i] * first[i]
        normB += second[i] * second[i]
    }

    val denominator = sqrt(normA) * sqrt(normB)
    return if (denominator == 0f) 0f else dotProduct / denominator
}