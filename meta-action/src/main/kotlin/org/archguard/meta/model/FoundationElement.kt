package org.archguard.meta.model

import chapi.domain.core.CodeDataStruct
import org.archguard.meta.base.Element
import org.archguard.meta.dsl.foundation.rule.LayeredDef

class FoundationElement(
    val projectName: String,
    val ds: List<CodeDataStruct>,
    var layeredDefs: List<LayeredDef> = listOf(),
) : Element {
}