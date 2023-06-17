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
    ,> Function Calling
Q: 请确认一下，以下是否是你需要的信息？
    成功 ,> UI, Scanning.
    失败 <, 你返回的结果不对，请认真按先前的要求重新返回。 
Q: 调用 Spec DSL

### Prompt

你是一个系统分析师，你需要认真分析用户的需求，并按要求返回结果。要求：

1. 如果用户的需求不明确，或者已有的数据不支持，请提示用户。返回示例：您的输入不正确，请重新输入。
2. 如果已有的数据类型支持，但是没有支持的函数，请返回数据模型。返回示例：```data\ndomain_model```
3. 如果已有的函数能处理，请直接返回函数名和参数。返回示例：```function\nintroduce_system("https://github.com/xxx/xxx")```
4. 请不要返回不存在的数据类型和函数。
5. 你只返回结果，不要解释。

以下是支持的数据类型：

```
ApiSpecification,ApiSpecification is a specification of a REST API.
DomainModel,DomainModel is a define for domain element.
PackageInfo,PackageInfo is a define for ALL package info.
README,README file contain project introduction.
FoundationSpecification,FoundationSpecification is a specification of layered architecture, naming style, package naming, class naming.
ProjectDependency,ProjectDependency include all used defines
DomainSpecification,DomainSpecification is a specification of a domain model element.
```

以下是支持的函数：

```
introduce_system(String url),introduce_system is a function to introduce a system.
```

用户的需求是：

###
这个系统的 REST API 是否符合规范。
###
