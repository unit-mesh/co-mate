package org.archguard.comate.action

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.nio.file.Path

class IntroductionPrompterTest {
    @Test
    fun should_generate_correct_prompt_test() {
        val workdir = javaClass.getResource("/hello-world").path
        val prompter = IntroductionPrompter(Path.of(workdir))

        assertEquals(
            """
dependencies: 

| path | deps |
| --- | --- |
| build.gradle.kts | org.junit.jupiter:junit-jupiter, com.google.guava:guava |

all channel types: [Website, Mobile App, Phone, Email, Social Media, Online Chat, In-person Store, Self-service Kiosk, Voice Assistant, Video Conferencing, SMS, Fax, Mail, Interactive Voice Response, Virtual Reality, Augmented Reality]
""", prompter.getExtendData()
        )
    }
}