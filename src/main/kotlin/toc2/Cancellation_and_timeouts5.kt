package toc2

import kotlinx.coroutines.*

fun main() = runBlocking {
    val job = launch {
        try {
            repeat(1000) { i ->
                println("job: I'm sleeping $i ...")
                delay(500L)
            }
        } finally {
            withContext(NonCancellable) {
                println("job: I'm running finally")
                delay(1000L)
                println("job: And I've just delayed for 1 sec because I'm non-cancellable")
            }
        }
    }
    delay(1300L) // delay a bit
    println("main: I'm tired of waiting!")
    job.cancelAndJoin() // cancels the job and waits for its completion
    println("main: Now I can quit.")
}

/**
 * Run non-cancellable block
 *

 이전 예제에서, 이미 CancellationException 이 발생한 코루틴의 finally 블록 안에서
 중단함수(suspending function)를 호출할 경우, CancellationException 이 발생됩니다.
 보통 (파일을 종료하거나, 작업을 취소하거나, 통신 채널을 종료하는 것과 같은) 잘 종료되어지는 작업들은
 넌블럭킹 (non-blocking) 으로 동작하지 때문에,
 어떠한 suspending function 으로 동작되어도 문제가 되지 않습니다.
 하지만, 이미 취소된 코루틴 안에서 이 후 동작이 동기적으로 중단함수를 호출해야 하는 상황이라면,
 `withContext(NonCancellable) {...}` 처럼 withContext 함수와 NonCancellable 컨텍스트를
 사용를 사용하여 처리가 가능합니다.
 (저는 withContext(NonCancellable) 을 활용하여 suspend 를 보장한다. 정도로 이해했습니다.)

 */