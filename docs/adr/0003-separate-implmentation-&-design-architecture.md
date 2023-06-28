# 3. separate implmentation & design architecture

Date: 2023-06-28

## Status

2023-06-28 proposed

## Context

We need to separate the implementation and design architecture.

use implementation architecture to generate code, use design architecture to generate document.

```kotlin
system("") {
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
```

## Decision

Decision here...

## Consequences

Consequences here...
