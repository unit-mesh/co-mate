# Principles of Governance


### Sample from specs

URI: http://example.com/api/petstore/v1/pets/dogs?breed=golden-retriever

### Action for URI

- Not Delete

### All Enum in List

```
enum class HttpStatusCode(val value: Int, val message: String) {
    OK(200, "OK"),
    CREATED(201, "Created"),
    NO_CONTENT(204, "No Content"),
    NOT_MODIFIED(304, "Not Modified"),
    BAD_REQUEST(400, "Bad Request"),
    UNAUTHORIZED(401, "Unauthorized"),
    FORBIDDEN(403, "Forbidden"),
    NOT_FOUND(404, "Not Found"),
    UNPROCESSABLE_ENTITY(422, "Unprocessable Entity"),
    INTERNAL_SERVER_ERROR(500, "Internal Server Error")
}
```

### Specific case for rule

Security

indepedence 