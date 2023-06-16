package org.archguard.comate.action

import org.archguard.comate.code.IntroductionCodePrompt
import org.archguard.comate.command.ComateWorkspace
import org.archguard.comate.strategy.BasicPromptStrategy
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.nio.file.Path
import kotlin.test.Ignore

class IntroductionPrompterTest {
    // todo: fix for Windows
    @Test
    @Ignore
    fun should_generate_correct_prompt_test() {
        val workdir = javaClass.classLoader.getResource("hello-world")?.path.orEmpty()
        val introduction = IntroductionCodePrompt(ComateWorkspace(Path.of(workdir), "kotlin", null), BasicPromptStrategy())

        assertEquals(
            """
Project introduction: Reflekt is a compile-time reflection library that leverages the flows of the
standard reflection approach and can find classes, objects (singleton classes) or functions
by some conditions in compile-time.
            
dependencies: 

| path | deps |
| --- | --- |
| build.gradle.kts | org.junit.jupiter:junit-jupiter, com.google.guava:guava |

all channel types: [Website, Mobile App, Phone, Email, Social Media, Online Chat, In-person Store, Self-service Kiosk, Voice Assistant, Video Conferencing, SMS, Fax, Mail, Interactive Voice Response, Virtual Reality, Augmented Reality]
""", introduction.getExtendData()
        )
    }
}