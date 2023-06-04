# ArchGuard CoMate

> Co-mate is an AI-powered software architecture copilot, design and governance tools.

## Todo

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
- [ ] Content parser
    - Code analyser by ArchGuard
        - [x] Dependency parser: `Gradle`, `Maven`, `Gomod`, `NPM`
        - [x] Source code parser: `Java`, `Kotlin`, `Go`, `JavaScript`, `TypeScript`
    - [x] README.md parser: `Markdown`
    - [ ] K8s parser: `YAML`
    - [ ] Dockerfile parser: `Dockerfile`
    - [ ] CI/CD parser: `Github Action`

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
