# 2. neighbor architecture generative frameworks

Date: 2023-06-27

## Status

2023-06-27 proposed

## Context

First

use the follow format to define architecture for an online mall system,

```kotlin
context("Cinema") {
    aggregate("Cinema") {
        entity("Cinema", "ScreeningRoom", "Seat")
    }
}
```

Output:

```kotlin
context("OnlineMall") {
    aggregate("OnlineMall") {
        entity("Mall", "Store", "Product", "Customer", "Order")
    }
}
```

Second, create generate Customer entity, use Kotlin data class to representation.


Third, according to the Customer entity, generate Product aggregate.

```kotlin
data class Customer(
    val id: String,
    val name: String,
    val email: String,
    val address: String,
    val phone: String,
    val dateOfBirth: LocalDate
)
```

Output: 

```kotlin
data class Product(
    val id: String,
    val name: String,
    val description: String,
    val price: Double,
    val quantityAvailable: Int
)
```

## Decision

Decision here...

## Consequences

Consequences here...
