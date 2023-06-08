# ArchGuard Co-mate

> Co-mate is an AI-powered software architecture copilot, design and governance tools.

## Todo

### BAU

- [x] Analysis project
- [ ] Analysis business logic
    - [x] Main.main function
    - [ ] Spring Boot application
- [ ] Architecture alignment
    - [ ] ADR with embedding
- [ ] Api governance by specs
    - [ ] GPT: Design Specification to functionality
    - [x] Functionality to DSL
    - [x] Construction governance by dynamic DSL
- [ ] Suggestions
    - [ ] pattern design
- [ ] Design by specs with examples
    - [ ] Functionality for DSL

### Feature Spike

- [ ] Refactor
    - [ ] architecture modification ?
    - [ ] MVC to DDD
    - [ ] Language migrations
        - [ ] JavaScript to Typescript ?

### Technology

- [ ] Indexes and Search
    - [ ] Embedding API for Kotlin/Java
    - [ ] Embedding Database
- [ ] [SentenceTransformers](https://huggingface.co/sentence-transformers) for Local Command
    - [x] Onnx Spike
        - [x] KotlinDL: [https://github.com/Kotlin/kotlindl](https://github.com/Kotlin/kotlindl)
        - [x] Deep Java Library: https://github.com/deepjavalibrary/djl
            - [x] [Huggingface tokenizers](https://github.com/deepjavalibrary/djl/tree/master/extensions/tokenizers)
    - [x] Onnx Model
        - [x] use Bloop converted: https://github.com/BloopAI/bloop/tree/main/model
    - Usecases examples:
        - [ ] analysis system ： https://github.com/archguard/ddd-monolithic-code-sample
        - [ ] refactor function ：`comate-cli`
- [ ] LangChain
    - JVM version: [LangTorch](https://github.com/Knowly-ai/langtorch)
- [ ] Token usage
    - [JTokkit](https://github.com/knuddelsgmbh/jtokkit)
- [ ] Content parser
    - Code analyser by ArchGuard
        - [x] Dependency parser: `Gradle`, `Maven`, `Gomod`, `NPM`
        - [x] Source code parser: `Java`, `Kotlin`, `Go`, `JavaScript`, `TypeScript`
    - [x] README.md parser: `Markdown`
    - [ ] K8s parser: `YAML`
    - [ ] Dockerfile parser: `Dockerfile`
    - [ ] CI/CD parser: `Github Action`
- [ ] Rust ML RPC Server
    - [ ] JSON Rpc Server
    - [ ] Wrapper for ML embedding
        - HuggingFace Tokenizers
        - SentenceTransformers
        - Onnx Runtime
        - Tiktoken: https://github.com/openai/tiktoken

## To Spike

- Architecture Documentation
    - [ ] README.md
    - [ ] ADR
- Business Architecture
    - [ ] By function call
    - [ ] By Org Function
- Architecture Governance
    - [ ] By Rule
- System integration

License
---

This code is distributed under the MPL license. See `LICENSE` in this directory.
