package org.archguard.architecture.action

interface RefactoringAction {
    fun execute()
}

/**
 * Refactoring to design-pattern's type
 */
enum class RefactoringToPatternType(val displayName: String) {

}