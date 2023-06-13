package org.archguard.spec.runtime.compiler

import org.jetbrains.kotlinx.jupyter.libraries.LibraryResolver

fun extendLibraries(): LibraryResolver {
    return listOf<Pair<String, String>>().toLibraries()
}
