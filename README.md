# ArchGuard CoMate

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
    - [ ] Functionality to DSL
    - [ ] Construction by dynamic DSL
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

## Know Issues

Crash with Onnx model:

```bash
Stack: [0x0000700004a28000,0x0000700004b28000],  sp=0x0000700004b27650,  free space=1021k
Native frames: (J=compiled Java code, j=interpreted, Vv=VM code, C=native code)
V  [libjvm.dylib+0x4c94]  AccessInternal::PostRuntimeDispatch<G1BarrierSet::AccessBarrier<548964ull, G1BarrierSet>, (AccessInternal::BarrierType)2, 548964ull>::oop_access_barrier(void*)+0x4
V  [libjvm.dylib+0x598d46]  jni_GetStringUTFChars+0xc6
C  [libonnxruntime4j_jni.dylib+0x90a7]  Java_ai_onnxruntime_OrtSession_createSession__JJLjava_lang_String_2J+0x37
j  ai.onnxruntime.OrtSession.createSession(JJLjava/lang/String;J)J+0
j  ai.onnxruntime.OrtSession.<init>(Lai/onnxruntime/OrtEnvironment;Ljava/lang/String;Lai/onnxruntime/OrtAllocator;Lai/onnxruntime/OrtSession$SessionOptions;)V+14
j  ai.onnxruntime.OrtEnvironment.createSession(Ljava/lang/String;Lai/onnxruntime/OrtAllocator;Lai/onnxruntime/OrtSession$SessionOptions;)Lai/onnxruntime/OrtSession;+8
j  ai.onnxruntime.OrtEnvironment.createSession(Ljava/lang/String;Lai/onnxruntime/OrtSession$SessionOptions;)Lai/onnxruntime/OrtSession;+7
j  org.archguard.comate.smart.Semantic$Companion.create()Lorg/archguard/comate/smart/Semantic;+153
j  org.archguard.comate.MainKt.main([Ljava/lang/String;)V+41
v  ~StubRoutines::call_stub
V  [libjvm.dylib+0x52d5b2]  JavaCalls::call_helper(JavaValue*, methodHandle const&, JavaCallArguments*, JavaThread*)+0x2a2
V  [libjvm.dylib+0x59075c]  jni_invoke_static(JNIEnv_*, JavaValue*, _jobject*, JNICallType, _jmethodID*, JNI_ArgumentPusher*, JavaThread*)+0x13c
V  [libjvm.dylib+0x593b09]  jni_CallStaticVoidMethod+0x189
C  [libjli.dylib+0x4f1d]  JavaMain+0x9cd
C  [libjli.dylib+0x76e9]  ThreadJavaMain+0x9
C  [libsystem_pthread.dylib+0x61d3]  _pthread_start+0x7d
C  [libsystem_pthread.dylib+0x1bd3]  thread_start+0xf
```

License
---

This code is distributed under the MPL license. See `LICENSE` in this directory.
