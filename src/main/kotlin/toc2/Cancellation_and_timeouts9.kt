package toc2

import kotlinx.coroutines.*

fun main() {
    runBlocking {
        repeat(100_000) { // Launch 100K coroutines
            launch {
                var resource: Resource? = null // Not acquired yet
                try {
                    withTimeout(60) { // Timeout of 60 ms
                        delay(50) // Delay for 50 ms
                        resource = Resource() // Store a resource to the variable if acquired
                    }
                    // We can do something else with the resource here
                } finally {
                    resource?.close() // Release the resource if it was acquired
                }
            }
         }
      }
    // Outside of runBlocking all coroutines have completed
    println(acquired) // Print the number of resources still acquired
}

/**
 * Asynchronous timeout and resources (2)
 *

 이 문제를 해결하려면 withTimeout 코드 블록에서 리소스를 반환하는 대신
 변수에 대한 참조를 저장하도록 해야 합니다.
 이 예제는 항상 0을 출력하고, Resources 는 메모리 릭이 발생되지 않습니다.

 */
