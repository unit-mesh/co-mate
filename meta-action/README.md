# Meta Action

Dynamic action for spec execution.

## Overview

The meta action is a dynamic action that can be used to execute a spec. For examples:

```kotlin
governance {
  construction {
    rule("")
    example("")
  }
    
  http_action = [ GET, POST, PUT, DELETE, PATCH, HEAD, OPTIONS, TRACE ]      
  status_code = [ OK, CREATED, NO_CONTENT, NOT_MODIFIED, BAD_REQUEST, UNAUTHORIZED, FORBIDDEN, NOT_FOUND, UNPROCESSABLE_ENTITY, INTERNAL_SERVER_ERROR ]

  security("""
*   Token Based Authentication (Recommended)  
      Ideally, microservices should be stateless so the service instances can be scaled out easily and the client requests can be routed to multiple independent service providers. A token based authentication mechanism should be used instead of session based authentication
*   API-Token Authentication (Recommended)
      ONAP is supposed to be accessed by third-party apps such as OSS/BSS, in the authentication process a user is not involved. API-Token can be used in such cases.
*   Centralized Authentication/Authorization (Recommended)
The MSB API Gateway can serve as the entry point to authenticate client requests and forwards them to the backend services, which might in turn invoke other services.
MSB doesn't do the authentication itself, instead, MSB can work with a security provider to provide centralized Authentication or ONAP with its pluggable architecture.
*   Use https for external communication for security reason
    The MSB External API Gateway can translate the https request from the external systems to light weight http communication inside ONAP system. The individual projects don't have to handle the trivial details such as certification configuration and avoid the overhead of https inside ONAP system.
""")  
}
```

