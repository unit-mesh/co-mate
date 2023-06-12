package org.archguard.architecture.style

enum class NamingStyle(val value: String) {
    CamelCase("CamelCase"),
    SnakeCase("SnakeCase"),
    KebabCase("KebabCase")
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
}