package org.archguard.architecture.style

enum class NamingStyle(val value: String) {
    CamelCase("CamelCase") {
        override fun isValid(string: String): Boolean {
            return string.matches(Regex("[a-z]+([A-Z][a-z]+)*"))
        }
    },
    SnakeCase("SnakeCase") {
        override fun isValid(string: String): Boolean {
            return string.matches(Regex("[a-z]+(_[a-z]+)*"))
        }
    },
    KebabCase("KebabCase") {
        override fun isValid(string: String): Boolean {
            return string.matches(Regex("[a-z]+(-[a-z]+)*"))
        }
    }
    ;

    companion object {
        fun contains(string: String): Boolean {
            val lowercase = string.lowercase()
            return NamingStyle.values().any { it.value.lowercase() == lowercase }
        }

        fun valuesString(): String {
            return NamingStyle.values().joinToString(", ") { it.value }
        }
    }

    abstract fun isValid(string: String): Boolean
}