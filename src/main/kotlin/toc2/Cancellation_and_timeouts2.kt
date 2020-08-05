package toc2

import kotlinx.coroutines.*

fun main() = runBlocking {
    val startTime = System.currentTimeMillis()
    val job = launch(Dispatchers.Default) {
        var nextPrintTime = startTime
        var i = 0
        while (i < 5) { // computation loop, just wastes CPU
            // print a message twice a second
            if (System.currentTimeMillis() >= nextPrintTime) {
                println("job: I'm sleeping ${i++} ...")
                nextPrintTime += 500L
            }
        }
    }
    delay(1300L) // delay a bit
    println("main: I'm tired of waiting!")
    job.cancelAndJoin() // cancels the job and waits for its completion
    println("main: Now I can quit.")
}

/**
 * Cancellation is cooperative
 *

 코루틴은 취소에 협조적(cooperative) 입니다.
 (참조, 취소에 협조적이라는 것은 이 후 예제코드들과 이어집니다.)

 코루틴 코드는 취소할 수 있어야 합니다. kotlinx.coroutines 의 모든 중단함수 (suspending function)들은
 취소가 가능합니다. 그들(*kotlinx.coroutines 의 중단함수)은 코루틴의 취소를 확인하고,
 취소를 예외로 던지게 됩니다. (CancellationException)

 하지만, 만약 코루틴이 연산 작업중 취소 여부를 확인 하지 않으면, 예시처럼 취소 동작이 취소되지 않습니다.
 예시 코드는 취소 후에도 5회 실행 되고 job이 종료됩니다.

 ------------------------------------
 참조) 위 예제 실행 결과는 다음과 같습니다.

 ------------------------------------
    job: I'm sleeping 0 ...
    job: I'm sleeping 1 ...
    job: I'm sleeping 2 ...
    main: I'm tired of waiting!
    job: I'm sleeping 3 ...
    job: I'm sleeping 4 ...
    main: Now I can quit.
 ------------------------------------

      앞 예제에서도 확인 하였듯이, cancel 과 cancel 후 join 을 호출하면 동작이 다르게 됩니다.
      위 예제 코드에서 cancelAndJoin 대신, join 만 호출할 경우 아래와 같은 결과를 확인할 수 있습니다.

 ------------------------------------
    job: I'm sleeping 0 ...
    job: I'm sleeping 1 ...
    job: I'm sleeping 2 ...
 >  main: I'm tired of waiting!
 >  main: Now I can quit.
    job: I'm sleeping 3 ...
    job: I'm sleeping 4 ...
 ------------------------------------

 */