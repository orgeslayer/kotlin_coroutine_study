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
            println("job: I'm running finally")
        }
    }
    delay(1300L) // delay a bit
    println("main: I'm tired of waiting!")
    job.cancelAndJoin() // cancels the job and waits for its completion
    println("main: Now I can quit.")
}

/**
 * Closing resources with finally
 *

 취소가 가능한 suspending function 의 경우, CancellationException 이 발생되므로
 일반적인 방법을 통해 처리가 가능합니다. 예를들어, 코틀린의 `try {...} finally {...}` 표현은
 코루틴이 취소될 때 정상적으로 실행할 수 있습니다.

 join() 및 cancelAndJoin() 함수 모두 작업이 완료될 때 까지 기다리기 때문에,
 위의 예시 실행 결과는 아래와 같이 출력됩니다.

 ------------------------------------
    job: I'm sleeping 0 ...
    job: I'm sleeping 1 ...
    job: I'm sleeping 2 ...
    main: I'm tired of waiting!
    job: I'm running finally
    main: Now I can quit.
 ------------------------------------

 참조) 위 코드에서 만약 cancel() 만 호출할 경우, 아래와 같이 finally 블록이 나중에 실행되는 것을
 확인할 수 있습니다. finally 구문도 코루틴에 포함되는 코드블록으로 이해하면 될 거 같습니다.
 따라서, 실행 순서가 예측 불가능하게 동작하지 않도록 cancel 대신 cancelAndJoin 을 사용하는 것을 추천합니다.

 ------------------------------------
    job: I'm sleeping 0 ...
    job: I'm sleeping 1 ...
    job: I'm sleeping 2 ...
    main: I'm tired of waiting!
 >  main: Now I can quit.
 >  job: I'm running finally
 ------------------------------------

 */