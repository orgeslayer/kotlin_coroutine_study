package toc7

import kotlinx.coroutines.*

fun main() = runBlocking {
    val job = launch {
        val child = launch {
            try {
                delay(Long.MAX_VALUE)
            } finally {
                println("Child is cancelled")
            }
        }
        yield()
        println("Cancelling child")
        child.cancel()
        child.join()
        yield()
        println("Parent is not cancelled")
    }
    job.join()
}

/**
 * - Cancellation and exceptions (1)
 *

 취소 동작은 예외(exception) 발생과 매우 밀접한 연관이 있습니다.
 코루틴은 내부적으로 취소에 대하여 CancellationException 이 발생시키며
 이러한 예외는 모든 핸들러에 의해 무시되므로, catch 코드블록으로 확인하게되는
 예외처리는 추가적으로 디버깅 목적으로만 사용해야 합니다. Job.cancel 을 이용하여
 코루틴이 취소될 때, 해당 코루틴은 종료되지만, 그 부모코루틴을 취소하지는 않습니다.

 이 예제코드는 다음과 같이 출력됩니다.
 ---------------------------------------------
    Cancelling child
    Child is cancelled
    Parent is not cancelled
 ---------------------------------------------




 */