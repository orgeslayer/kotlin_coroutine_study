package toc7

import kotlinx.coroutines.*
import java.io.IOException
import java.lang.NullPointerException

fun main() = runBlocking {
    val handler = CoroutineExceptionHandler { _, exception ->
        println("CoroutineExceptionHandler got $exception")
    }
    val job = GlobalScope.launch(handler) {
        val inner = launch { // all this stack of coroutines will get cancelled
            launch {
                launch {
                    throw IOException() // the original exception
                }
            }
        }
        try {
            inner.join()
        } catch (e: CancellationException) {
            println("Rethrowing CancellationException with original cause")
            throw e // cancellation exception is rethrown, yet the original IOException gets to the handler
//            throw NullPointerException()
        }
    }
    job.join()
}

/**
 * - Exceptions aggregation (2)
 *

 취소 예외의 경우 기본적으로 이러한 메커니즘에 포함되지 않으며, 예외처리 집계에 대하여 투명합니다.
 이 예제는 다음과 같이 출력됩니다.

 ------------------------------------
    Rethrowing CancellationException with original cause
    CoroutineExceptionHandler got java.io.IOException
 ------------------------------------

 Note) catch { } 코드블록에서 CancellationException 을 throw 하지 않고
 다른 예외를 발생켰을 때 CoroutineExceptionHandler 에서 전달받는 exception.suppressed 를 확인해보세요.

 */