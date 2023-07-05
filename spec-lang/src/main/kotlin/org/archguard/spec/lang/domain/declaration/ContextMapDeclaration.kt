package org.archguard.spec.lang.domain.declaration

import org.archguard.spec.lang.domain.MappingDefine

class ContextMapDeclaration(val name: String) {
    fun context(name: String, function: ContextDeclaration.() -> Unit): ContextDeclaration {
        val contextDeclaration = ContextDeclaration(name)
        contextDeclaration.function()
        return contextDeclaration
    }

    fun context(name: String): ContextDeclaration = ContextDeclaration(name)

    fun mapping(block: MappingDefine.() -> Unit): MappingDefine {
        val mapping = MappingDefine()
        mapping.block()
        return mapping
    }
}