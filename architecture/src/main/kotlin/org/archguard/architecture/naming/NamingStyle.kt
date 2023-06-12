package org.archguard.architecture.naming

enum class NamingStyle(val value: String) {
    CamelCase("CamelCase"),
    SnakeCase("snake_case"),
    KebabCase("kebab-case"),
    Unknown("unknown")
    ;

    companion object {
        fun contains(string: String): Boolean {
            val lowercase = string.lowercase()
            return NamingStyle.values().any { it.value == lowercase }
        }

        fun valuesString(): String {
            return NamingStyle.values().joinToString(", ") { it.value }
        }
    }
}