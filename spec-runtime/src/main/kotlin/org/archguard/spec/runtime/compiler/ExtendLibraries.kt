package org.archguard.spec.runtime.compiler

import org.jetbrains.kotlinx.jupyter.libraries.LibraryResolver


val exposedLibDef = SimpleLibraryDefinition(
    imports = listOf(
    ),
    dependencies = listOf(
    )
)


fun extendLibraries(): LibraryResolver {
//    val exposed = "exposed" to Json.encodeToString(exposedLibDef)
    return listOf<Pair<String, String>>().toLibraries()
}
