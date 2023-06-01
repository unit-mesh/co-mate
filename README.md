# ArchGuard CoMate

> Co-mate is an AI-powered software architecture copilot, design and governance tools.

## Todo

- [ ] Indexes and Search
    - [ ] Apache Arrow
    - [ ] Embedding API for Kotlin/Java
    - [ ] Embedding Database
- [ ] [SentenceTransformers](https://huggingface.co/sentence-transformers) for Local Command
    - [ ] Onnx Spike
        - [ ] https://github.com/JetBrains-Research/kinference
        - [ ] https://onnxruntime.ai/docs/get-started/with-java.html
    - [ ] Onnx Model
        - [ ] use Bloop converted: [https://github.com/BloopAI/bloop/tree/main/model]
        - [ ] or convert by ourselves.
    - Usecase examples:
        - analysis system ： https://github.com/archguard/ddd-monolithic-code-sample
        - refactor function ：`comate-cli`
- [ ] Agent API
- [ ] Command
- [ ] JVM version LangChain
    - [LangTorch](https://github.com/Knowly-ai/langtorch)
- [ ] Content parser
    - [ ] Dependency parser: `Gradle`, `Maven`, `Cargo`, `NPM`, `Pip`, `Gem
    - [ ] README.md parser: `Markdown`, `RST`
    - [ ] Source code parser: `Java`, `Kotlin`, `Python`, `Rust`, `C`, `C++`, `Go`, `JavaScript`, `TypeScript`, `Ruby`
    - [ ] K8s parser: `YAML`
    - [ ] Dockerfile parser: `Dockerfile`
    - [ ] CI/CD parser: `Jenkinsfile`, `CircleCI`, `TravisCI`, `Github Action`

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
