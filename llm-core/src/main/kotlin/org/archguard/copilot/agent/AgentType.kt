package org.archguard.copilot.agent

enum class AgentType(val value: String) {
    ZERO_SHOT_REACT_DESCRIPTION("zero-shot-react-description"),
    REACT_DOCSTORE("react-docstore"),
    SELF_ASK_WITH_SEARCH("self-ask-with-search"),
    CONVERSATIONAL_REACT_DESCRIPTION("conversational-react-description"),
    CHAT_ZERO_SHOT_REACT_DESCRIPTION("chat-zero-shot-react-description"),
    CHAT_CONVERSATIONAL_REACT_DESCRIPTION("chat-conversational-react-description"),
    STRUCTURED_CHAT_ZERO_SHOT_REACT_DESCRIPTION("structured-chat-zero-shot-react-description")
}

