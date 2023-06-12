package org.archguard.meta.model

import chapi.domain.core.CodeDataStruct
import org.archguard.meta.base.Element

class FoundationElement(
    val projectName: String,
    val ds: List<CodeDataStruct>,
) : Element() {
}