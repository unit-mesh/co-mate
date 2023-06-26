package org.archguard.spec.lang.matcher

enum class CompareType {
    ENDS_WITH,
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
            CompareType.ENDS_WITH -> {
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

    override fun toString(): String {
        val infix = when(equal) {
            true -> "shouldBe"
            false -> "shouldNotBe"
        }

        val compare = when(compareType) {
            CompareType.ENDS_WITH -> "endsWith"
            CompareType.STARTS_WITH -> "startsWith"
            CompareType.CONTAINS -> "contains"
        }

        return """$left $infix $compare("${right.joinToString("\", \"")}")"""
    }
}