package basic

import kotlinx.coroutines.*

fun main() = runBlocking { // this: CoroutineScope
    launch { // launch a new coroutine in the scope of runBlocking
        delay(1000L)
        println("World!")
    }
    println("Hello,")
}

/**
 하지만 아직 실용적으로 사용하기에는 약간 아쉬운점이 있습니다.
 GlobalScope.launch{ } 로 생성한 코루틴은 최상위 코루틴을 생성하여,
 실행하는 동안 일부 메모리 자원을 소모합니다. 만약, 너무 많은 코루틴을 생성 및 실행하고,
 명시적으로 각 참조 코루틴들을 join 하는것은 오류가 발생할 수 있습니다. (메모리가 부족하면 어떻게 될까요?)

 좀 더 나은 방법으로 코드에서 좀 더 구조화된 동시성을 구현 할 수 있습니다.
 모든 코루틴은 각자의 스코프를 가지고 있습니다.
 GlobalScope.launch{ } 대신,
 보통 스레드 생성하는것 처럼 - eg. 스레드는 항상 글로벌입니다. -
 launch{ } 로 새로운 코루틴 코드를 작성하면
 runBlocking{ } 스코프(부모스코프)에서 실행되며,
 종료될 때 까지 대기되어 join() 을 명시적으로 호출할 필요가 없습니다.
 */
