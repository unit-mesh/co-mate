# 3. separate implmentation & design architecture

Date: 2023-06-28

## Status

2023-06-28 proposed

## Context

We need to separate the implementation and design architecture.

use implementation architecture to generate code, use design architecture to generate document.

```kotlin
system("", organization = "") {
    service("") {
        module("") {
            component("") {
                layered("")
            }
        }
    }
}
```

use design architecture to create blueprint, communicate with others. We propose the DDD strategic design architecture:

```kotlin
context("Cinema") {
    aggregate("Cinema") {
        entity("Cinema", "ScreeningRoom", "Seat")
    }
}

biz {

    infrastructure("Log", "Email", "SMS")
}
```

design DSL for composable design with business capabilities:

```kotlin
enum class CapabilityType {
    BASE,
    DATA,
    FULL,
    MINI,
    PROCESS
}

application {
    modular("Module1") {
        capability("Capability1", CapabilityType.BASE) {
            // 定义组成结构
            data {
                // 内部数据和元数据
            }

            services {
                // 服务/微服务
            }

            interfaces {
                // 对外开放的API接口
            }

            events {
                // 事件通道
            }

            // 可选的用户交互等能力
        }
        capability("Capability2", CapabilityType.DATA) {
            // 设置能力的属性和配置
        }
    }
    modular("Module2") {
        capability("Capability3", CapabilityType.FULL) {
            // 设置能力的属性和配置
        }
        capability("Capability4", CapabilityType.MINI) {
            // 设置能力的属性和配置
        }
    }
}
```

## Decision

Decision here...

## Consequences

Consequences here...
