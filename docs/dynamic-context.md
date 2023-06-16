# Dynamic Context

## Language API

- Nature Language In API
    - 语言转 Command：分析这个系统。
- Nature Language Out API
    - 根据用户的需求，选择合适的分析模型数据和模型函数。
        - Dynamic Context
    - 返回一个 UI 确认表单。
- Nature Re-Try API
    - 你返回的结果不对，请认真按先前的要求重新返回。
- Spec Language API
    - Foundation DSL
    - Domain DSL
    - RestAPI DSL

## Dynamic Context

对话：

Q：你好，我是 Co-mate，你想要什么？
A: 帮我分析一下这个系统。https://github.com/archguard/ddd-monolithic-code-sample
    => Function Calling
Q: 请确认一下，以下是否是你需要的信息？
    成功 => UI, Scanning.
    失败 <= 你返回的结果不对，请认真按先前的要求重新返回。 
Q: 调用 Spec DSL
