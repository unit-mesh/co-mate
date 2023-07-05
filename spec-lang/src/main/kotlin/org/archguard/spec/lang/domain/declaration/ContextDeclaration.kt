package org.archguard.spec.lang.domain.declaration

class ContextDeclaration(val name: String) {
    val relations: MutableList<Pair<ContextDeclaration, ContextDeclaration>> = mutableListOf()
    fun aggregate(name: String, function: AggregateDeclaration.() -> Unit): AggregateDeclaration {
        val aggregateDeclaration = AggregateDeclaration(name)
        aggregateDeclaration.function()
        return aggregateDeclaration
    }

    infix fun dependedOn(targetContextDeclaration: ContextDeclaration) {
//        relations +=
    }
}