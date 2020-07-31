package toc2

import kotlinx.coroutines.*

fun main() = runBlocking {
    val job = launch {
        repeat(4) { i ->
            println("job: I'm sleeping $i ...")
            delay(500L)
        }
        println("job: Now I'm finished")
    }
    delay(1300L) // delay a bit
    println("main: I'm tired of waiting!")
    job.cancel() // cancels the job
    job.join() // waits for job's completion
    println("main: Now I can quit.")
}

/**
 * Cancelling and Timeouts
 *
 * Cancelling coroutine execution
 *

 장시간 동작되는 애플리케이션에서는 백그라운드에서 동작되는 코루틴에 대하여 세밀한 제어를 원할 것입니다.
 예를들어, 사용자가 코루틴을 시작한 페이지를 닫았을 때, 이제 그 결과는 더 이상 필요하지 않고 요청된 동작(오퍼레이션)은 취소될 수 있습니다.
 launch 함수의 경우 코루틴에서 취소하는데 사용할 수 있는 Job을 반환합니다. (Basic3.kt 를 참조하세요)
 해당 코드를 실행 시키면 아래와 같은 결과를 확인할 수 있습니다.

 ------------------------------------
    job: I'm sleeping 0 ...
    job: I'm sleeping 1 ...
    job: I'm sleeping 2 ...
    main: I'm tired of waiting!
    main: Now I can quit.
 ------------------------------------

 메인(메인 코루틴, 위 코드에서는 runBlocking)이 job.cancel을 호출 즉시
 해당 코루틴 내용이 취소되었기 때문에, 더 이상 출력 결과를 확인할 수 없습니다.
 또한, cancel 과 join 을 동작 처리하는 cancelAndJoin 확장 함수도 있습니다.

 참조) cancel 만 호출할 경우 종료하라는 신호만 보냅니다.
      cancel 호출 후 join 을 호출하는 것은 해당 Job이 정상 종료할 때 까지 대기하기 위함입니다.
      예를들어, cancel 요청을 하였으나 해당 Job이 타이트하게 동작하는 단순 루프 코드를 수행하고 있다면
      delay 가 없자면 종료되지 않습니다.)

---------------------------------------------
 * Job 에 대한 상태는 아래 링크를 참조하면 이해하는데 도움이 됩니다.
 > https://thdev.tech/kotlin/2019/04/08/Init-Coroutines-Job/


 */