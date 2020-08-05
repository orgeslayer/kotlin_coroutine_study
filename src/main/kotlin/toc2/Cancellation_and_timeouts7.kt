package toc2

import kotlinx.coroutines.*

fun main() = runBlocking {
    val result = withTimeoutOrNull(1300L) {
        repeat(1000) { i ->
            println("I'm sleeping $i ...")
            delay(500L)
        }
        "Done" // will get cancelled before it produces this result
    }
    println("Result is $result")
}

/**
 * Timeout
 *

 취소는 단지 예외이므로, 모든 자원은 통상적인 방법으로 닫히게 됩니다. (closed)
 `try {...} catch (e: TimeoutCancellationException) {...}` 을 활용하여 타임아웃 발생 시
 예외처리를 하거나, withTimeout와 유상하지만 null을 리턴하는 withTimeoutOrNull 함수를 활용하여
 타임아웃 발생에 대한 예외 처리가 가능합니다.

 예제소스는 더 이상 코드가 동작 중 exception 이 발생되지 않습니다.

 ----------------------------------------------------
 참조1) try {...} catch {...} 와 관련된 내용은 이전 참조 내용 하단 코멘트를 참조하세요.
 참조2) withTimeoutOrNull 함수는 내부에서
    try {...} catch (e: TimeoutCancellableException) {...}
    로 구현되어 있어, 별도의 Exception 을 발생시키지 않습니다.
 */
