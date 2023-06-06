package org.archguard.spec

import org.junit.jupiter.api.Test

class ApiPartitionerTest {
    @Test
    fun sample_for_split() {
        val partitioner = ApiPartitioner(
            """

HTTP response status codes
==========================

The server should always return the right HTTP status code to the client. (Recommended)

*   Standard HTTP status codes

200 – OK – Everything is working  
201 – OK – New resource has been created  
204 – OK – The resource was successfully deleted

304 – Not Modified – The client can use cached data

400 – Bad Request – The request was invalid or cannot be served. The exact error should be explained in the error payload. E.g. „The JSON is not valid“  
401 – Unauthorized – The request requires a user authentication  
403 – Forbidden – The server understood the request but is refusing it or the access is not allowed.  
404 – Not found – There is no resource behind the URI.  
422 – Unprocessable Entity – Should be used if the server cannot process the entity, e.g. if an image cannot be formatted or mandatory fields are missing in the payload.
        """.trimIndent()
        )

        partitioner.partition()
    }
}