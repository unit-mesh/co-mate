package org.archguard.architecture.style

enum class NamingStyle(val value: String) {
    UpperCamelCase("UpperCamelCase") {
        override fun isValid(string: String): Boolean {
            return string.matches(Regex("[A-Z][a-zA-Z0-9]+"))
        }
    },
    CamelCase("CamelCase") {
        override fun isValid(string: String): Boolean {
            return string.matches(Regex("[a-zA-Z]+([A-Za-z0-9]+)*"))
        }
    },
    SnakeCase("SnakeCase") {
        override fun isValid(string: String): Boolean {
            return string.matches(Regex("[a-z]+(_[a-z0-9]+)*"))
        }
    },
    KebabCase("KebabCase") {
        override fun isValid(string: String): Boolean {
            return string.matches(Regex("[a-z]+(-[a-z0-9]+)*"))
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