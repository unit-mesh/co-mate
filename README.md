# ArchGuard Co-mate

[![Java support](https://img.shields.io/badge/Java-11+-green?logo=java&logoColor=white)](https://openjdk.java.net/)
![GitHub tag (latest by date)](https://img.shields.io/github/v/tag/archguard/co-mate)
[![Coverage Status](https://coveralls.io/repos/github/archguard/co-mate/badge.svg?branch=master)](https://coveralls.io/github/archguard/co-mate?branch=master)

> Co-mate is an AI-powered software architecture copilot, design and governance tools.

Project manage: https://github.com/orgs/archguard/projects/5/views/1

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

- `comate-core`: core logic, handle ArchGuard API, LLM logic, etc.
- LLM modules:
    - `llm-core`: large language model core, like OpenAI call, Token calculate, Tokenizer, Embedding, Similarity for
  Embedding, etc.
- Architecture Spec
    - `spec-runtime`: the Kotlin REPL/Interpreter for Architecture Spec DSL
    - `spec-partitioner`: the partitioner for partitioning the architecture spec document
- Architecture Define
   - `architecture`: architecture model

License
---

This code is distributed under the MPL license. See `LICENSE` in this directory.
