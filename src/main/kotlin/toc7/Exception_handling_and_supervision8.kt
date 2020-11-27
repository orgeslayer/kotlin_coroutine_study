package toc7

import kotlinx.coroutines.*
import java.io.IOException
import java.lang.NullPointerException

fun main() = runBlocking {
    val supervisor = SupervisorJob()
    with(CoroutineScope(coroutineContext + supervisor)) {
        // launch the first child -- its exception is ignored for this example (don't do this in practice!)
        val firstChild = launch(CoroutineExceptionHandler { _, _ ->  }) {
            println("The first child is failing")
            throw AssertionError("The first child is cancelled")
        }
        // launch the second child
        val secondChild = launch {
            firstChild.join()
            // Cancellation of the first child is not propagated to the second child
            println("The first child is cancelled: ${firstChild.isCancelled}, but the second one is still active")
            try {
                delay(Long.MAX_VALUE)
            } finally {
                // But cancellation of the supervisor is propagated
                println("The second child is cancelled because the supervisor was cancelled")
            }
        }
        // wait until the first child fails & completes
        firstChild.join()
        println("Cancelling the supervisor")
        supervisor.cancel()
        secondChild.join()
    }
}

/**
 * Supervision
 * - Supervision Job
 *

 SupervisorJob 은 이러한 목적에 맞춰 사용할 수 있습니다. 이는 취소가 아래쪽으로만 전파되는
 일반 Job과 유사합니다. 이것은 손쉽게 아래 예제 코드로 확인할 수 있습니다.

 예제 코드는 다음과 같이 출력됩니다.

 ------------------------------------
    The first child is failing
    The first child is cancelled: true, but the second one is still active
    Cancelling the supervisor
    The second child is cancelled because the supervisor was cancelled
 ------------------------------------

 참조) 4번에서 소개하는 여러개의 자식 코루틴중 하나가 취소된 경우와 함께 확인해 보세요.
 */