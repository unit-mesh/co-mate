package org.archguard.meta.model

import chapi.domain.core.CodeDataStruct
import org.archguard.meta.base.Element
import org.archguard.meta.dsl.foundation.declaration.LayeredDefine

class FoundationElement(
    val projectName: String,
    val ds: List<CodeDataStruct>,
    /**
     * we delay to fill this field until we have all the information we need
     * to fill it. like the layer name, the layer type, etc.
     */
    var layeredDefines: List<LayeredDefine> = listOf(),
) : Element {
}