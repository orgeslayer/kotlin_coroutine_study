package toc4

import kotlinx.coroutines.*

// fun log(msg: String) = println("[${Thread.currentThread().name}] $msg")

fun main() = newSingleThreadContext("Ctx1").use { ctx1 ->
    newSingleThreadContext("Ctx2").use { ctx2 ->
        runBlocking(ctx1) {
            log("Started in ctx1")
            withContext(ctx2) {
                log("Working in ctx2")
            }
            log("Back to ctx1")
        }
    }
}


/**
 * Jumping between threads
 *

 예제 코드를 '-Dkotlinx.coroutines.debug' JVM 옵션으로 실행해 봅니다. (디버그 내용 참조)
 ------------------------------------------------
    [Ctx1 @coroutine#1] Started in ctx1
    [Ctx2 @coroutine#1] Working in ctx2
    [Ctx1 @coroutine#1] Back to ctx1
 ------------------------------------------------

 이 코드는 몇 가지 새로운 기법을 보여줍니다.
 하나는 runBlocking 에 명시적으로 context 를 지정하는 것이고,
 다른 하나는 withContext 에 명시적으로 context를 선언함으로써
 동일한 코루틴을 유지하면서 context를 변경하는 것입니다.

 이 예제는 newSingleThreadContext 로 만든 스레드가 더 이상 필요하지 않을 경우
 해제 하기 위해 Kotlin 표준 라이브러리의 use 함수를 사용한 것에 유의하세요.
 */


