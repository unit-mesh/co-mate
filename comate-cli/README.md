# Kotlin ML

- JDL -> Java Deep Learning
- KotlinDL -> Kotlin Deep Learning
- Algorithmia 
  - [ ] cosine similarits: https://huggingface.co/tasks/sentence-similarity

```kotlin
val model = OnnxInferenceModel("element/element.onnx")
val detections = model.inferAndCloseUsing(ExecutionProvider.CPU()) {
    //
}
```

NDARRAY

```kotlin

val manager = NDManager.newBaseManager()
val inputIdsArray = manager.create(inputIds)
val attentionMaskArray = manager.create(attentionMask)
val tokenTypeIdsArray = manager.create(typeIds)

```
