# ArchGuard Co-mate

[![Java support](https://img.shields.io/badge/Java-11+-green?logo=java&logoColor=white)](https://openjdk.java.net/)
![GitHub tag (latest by date)](https://img.shields.io/github/v/tag/archguard/co-mate)
[![Coverage Status](https://coveralls.io/repos/github/archguard/co-mate/badge.svg?branch=master)](https://coveralls.io/github/archguard/co-mate?branch=master)

> Co-mate is an AI-powered software architecture copilot, design and governance tools.

## Usage

1. download the latest release from [release page](https://github.com/archguard/co-mate/releases)
2. please put `OPENAI_API_KEY=xxx` in `~/.comate/.env`
3. run `java -jar comate-cli-*-all.jar` replace `*` with the version number

`.env` file example:

```
OPENAI_API_KEY=xxx
OPENAI_API_PROXY=xxxx (optional, if you had a OpenAI proxy server)
```

## Modules

- `comate-cli`: command line interface
- `comate-gui`: graphical user interface

Core modules:

- `architecture`: architecture model
- `comate-core`: core logic, handle ArchGuard API, LLM logic, etc.
- `llm-core`: large language model core, like OpenAI call, Token calculate, Tokenizer, Embedding, Similarity for
  Embedding, etc.
- Architecture Spec
    - `spec-runtime`: the Kotlin REPL/Interpreter for Architecture Spec DSL
    - `spec-partitioner`: the partitioner for partitioning the architecture spec document

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
- [ ] Indexes and Search
    - [ ] Embedding API for Kotlin/Java
    - [ ] Embedding Database

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
