# Kotlin ML

- JDL -> Java Deep Learning
- KotlinDL -> Kotlin Deep Learning

```kotlin
val model = OnnxInferenceModel("model/model.onnx")
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
