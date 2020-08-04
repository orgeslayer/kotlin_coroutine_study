package toc2

import kotlinx.coroutines.*

fun main() = runBlocking {
    val startTime = System.currentTimeMillis()
    val job = launch(Dispatchers.Default) {
        var nextPrintTime = startTime
        var i = 0
        while (isActive) { // cancellable computation loop
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
 * Making computation code cancellable
 *

 연산중인 코드를 취소할 수 있게 만드는 두 가지 방법이 가능합니다.
 첫 번째는 취소를 확인하는 정지기능을 주기적으로 발동하는 것입니다.
 이 방법을 달성하기 위한 적합한 함수로 yield() 가 있습니다. (참조. yield > 양보하다)

 다른 방법으로는, 명시적으로 취소 상태인지를 체크하는 것입니다.
 후자의 방법으로 한번 접근 해 봅시다.

 이전 코드에서 `while (i < 5)` 코드 대신
 `while (isActive)` 로 변경하고, 다시 실행해 봅니다.

 실행 결과 반복 동작이 취소된 것을 확인할 수 있습니다.
 isActive는 코루틴 안에서(=내부에서) CoroutineScope 객체를 통해 접근이 가능한 확장프로퍼티 입니다.

 */