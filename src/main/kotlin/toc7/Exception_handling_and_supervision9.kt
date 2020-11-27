package toc7

import kotlinx.coroutines.*
import java.io.IOException
import java.lang.NullPointerException

fun main() = runBlocking {
    try {
        supervisorScope {
            val child = launch {
                try {
                    println("The child is sleeping")
                    delay(Long.MAX_VALUE)
                } finally {
                    println("The child is cancelled")
                }
            }
            // Give our child a chance to execute and print using yield
            yield()
            println("Throwing an exception from the scope")
            throw AssertionError()
        }
    } catch(e: AssertionError) {
        println("Caught an assertion error")
    }
}

/**
 * Supervision
 * - Supervision Scope
 *

 coroutineScope 대신 supervisorScope 을 이용하여 스코프에서 동시성 범위를 지정할 수 있습니다.
 이는 취소를 한 방향으로만 전파하고, 스스로 실패한 경우에만 모든 하위 코루틴을 취소시킵니다.
 또한 coroutineScope 처럼 모든 하위 코루틴들이 종료되는 것을 기다립니다.

 이 예제코드는 다음과 같이 출력됩니다.

 ------------------------------------
    The child is sleeping
    Throwing an exception from the scope
    The child is cancelled
    Caught an assertion error
 ------------------------------------

 */