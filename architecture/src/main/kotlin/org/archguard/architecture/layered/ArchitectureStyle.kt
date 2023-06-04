package org.archguard.architecture.layered

enum class ArchitectureStyle(val value: String) {
    Layered("layered"),
    Pipeline("pipeline"),
    MicroKernel("microkernel"),
    ServiceBased("servicebased"),
    Serverless("serverless"),
    EventDriven("eventdriven"),
    SpaceBased("spacebased"),
    Microservice("microservice"),
    Unknown("unknown")
    ;

    companion object {
        fun contains(string: String): Boolean {
            val lowercase = string.lowercase()
            return LayeredStyle.values().any { it.value == lowercase }
        }

        fun valuesString(): String {
            return LayeredStyle.values().joinToString(", ") { it.value }
        }
    }
}