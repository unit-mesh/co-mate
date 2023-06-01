package org.archguard.copilot

import org.archguard.comate.action.IntroductionPrompter
import java.io.File
import kotlin.io.path.Path

fun main() {
    val basepath = Path(File(".").absolutePath)
    val prompt = IntroductionPrompter(basepath).prompt()
    print(prompt)
}
