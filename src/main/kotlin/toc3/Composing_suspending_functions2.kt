package toc3

import kotlinx.coroutines.*
import kotlin.system.measureTimeMillis

//suspend fun doSomethingUsefulOne(): Int {
//    delay(1000L) // pretend we are doing something useful here
//    return 13
//}
//
//suspend fun doSomethingUsefulTwo(): Int {
//    delay(1000L) // pretend we are doing something useful here, too
//    return 29
//}

fun main() = runBlocking {
    val time = measureTimeMillis {
        val one = async { doSomethingUsefulOne() }
        val two = async { doSomethingUsefulTwo() }
        println("The answer is ${one.await() + two.await()}")
    }
    println("Completed in $time ms")
}

/**
 * Composing Suspending Functions
 *  - 이 장에서는 suspending functions 에 대한 다양한 접근 방법을 다룹니다.
 *
 * Concurrent using async
 *

 만약, doSomethingUsefulOne 함수와 doSomethingUsefulTwo 함수 호출 사이에
 서로 의존성이 없고 동시에 실행함으로써 더 빨리 원하는 결과(= 각 함수의 실행 결과)를
 를 먼저 얻으려면 어떻게 해야할까요? async 가 도움이 될 수 있습니다.

 개념적으로, async는 launch 와 같습니다. 차이점은 launch 의 경우 Job을 반환하고 결과값을 제공하지 않는 반면,
 async는 추후 결과를 제공한다는 것이 약속된, 경량화된 non-blocking future 인 Deferred 를 반환합니다.
 .await() 를 사용하여 최종 결과를 나중에 전달 받을 수 있지만, Deferred 또한 Job 타입이므로 만약 필요할 경우
 취소를 요청할 수 있습니다.

 예제에서, 두 개의 코루틴은 비동기적으로 실행되기 때문에 두 배 더 빠릅니다.
 코루틴의 동시성은 항상 명백하다는 것을 유의해야 합니다.

 -------------------------------------
 * 참조) Deferred - 미뤄지다, 연기하다. 연기된.
        Job을 구현하고 있는 인터페이스 입니다. (=결과값을 갖고있는 Job 입니다.)

 */