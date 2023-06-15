package org.archguard.spec.lang.matcher

enum class CompareType {
    END_WITHS,
    STARTS_WITH,
    CONTAINS
}

data class DelayCompare(
    var left: String,
    val compareType: CompareType,
    val right: List<String>,
    var equal: Boolean = true,
) {
    fun compare(): Boolean {
        val result = when (compareType) {
            CompareType.END_WITHS -> {
                right.any { left.endsWith(it) }
            }

            CompareType.STARTS_WITH -> {
                right.any { left.startsWith(it) }
            }

            CompareType.CONTAINS -> {
                right.any { left.contains(it) }
            }
        }

        return if (equal) result else !result
    }
}