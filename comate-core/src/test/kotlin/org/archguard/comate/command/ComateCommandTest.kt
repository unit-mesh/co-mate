package org.archguard.comate.command

import org.junit.jupiter.api.Assertions.*

class ComateCommandTest {
    @org.junit.jupiter.api.Test
    fun fromText() {
        val cmd = "introduction system"
        val comateCommand = ComateCommand.fromText(cmd)
        assertEquals(ComateCommand.Intro, comateCommand)
    }
}