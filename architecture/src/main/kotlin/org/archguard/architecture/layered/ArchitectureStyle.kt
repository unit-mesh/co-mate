package org.archguard.architecture.layered

enum class ArchitectureStyle(val value: String) {
    LAYERED("layered"),
    PIPELINE("pipeline"),
    MICROKERNEL("microkernel"),
    SERVICEBASED("servicebased"),
    SERVERLESS("serverless"),
    EVENTDRIVEN("eventdriven"),
    SPACEBASED("spacebased"),
    MICROSERVICE("microservice"),
    UNKNOWN("unknown")
    ;

    companion object {
        fun contains(string: String): Boolean {
            val lowercase = string.lowercase()
            return values().any { it.value == lowercase }
        }

        fun from(string: String): ArchitectureStyle {
            val lowercase = string.lowercase()
            return values().find { it.value == lowercase } ?: UNKNOWN
        }
    }
}