[![Run deteKT](https://github.com/JetBrains-Research/reflekt/actions/workflows/detekt.yml/badge.svg)](https://github.com/JetBrains-Research/reflekt/actions/workflows/detekt.yml)
[![Run diKTat](https://github.com/JetBrains-Research/reflekt/actions/workflows/diktat.yml/badge.svg)](https://github.com/JetBrains-Research/reflekt/actions/workflows/diktat.yml)

# Reflekt

Reflekt is a compile-time reflection library that leverages the flows of the
standard reflection approach and can find classes, objects (singleton classes) or functions
by some conditions in compile-time.

Instead of relying on JVM reflection, Reflekt performs compile-time resolution of reflection queries
using Kotlin compiler analysis, providing a convenient reflection API without actually using
reflection.

Reflekt is a joint project of [JetBrains Research](https://research.jetbrains.org/) and
the [Kotless](https://github.com/JetBrains/kotless) team. The main reason for its creation was the
necessity of GraalVM support in modern Java applications, especially on Serverless workloads. With
the help of the Reflekt project, Kotless will be able to provide access to GraalVM to users of
historically reflection-based frameworks such as Spring or their own Kotless DSL.

We have implemented two approaches - searching classes\objects or functions via a limited DSL
and by custom user condition via an extended DSL.
The first one will be called `Reflekt`, and the second `SmartReflekt`.

**Restrictions**. Reflekt analyses only `.kt` files (in the project and in the libraries); uses
Kotlin `1.7.0`. Reflekt does not currently support incremental compilation.
