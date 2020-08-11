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

suspend fun concurrentSum(): Int = coroutineScope {
    val one = async { doSomethingUsefulOne() }
    val two = async { doSomethingUsefulTwo() }
    one.await() + two.await()
}

fun main() = runBlocking {
    val time = measureTimeMillis {
        println("The answer is ${concurrentSum()}")
    }
    println("Completed in $time ms")
}

/**
 * Structured concurrency with async
 *

 async를 활용한 비동기 처리 예제에서(=2번째 예제입니다.) doSomethingUsefulOne() 과
 doSomethingUsefulTwo() 함수를 각자 수행하여(비동기적으로 수행하여) 각각의 합을 리턴하는 함수를 작성해 봅시다.
 async() 코루틴 빌더는 CoroutineScope 의 확장함수로 선언 되어있기 때문에 이 빌더를 코루틴 스코프 내에서 호출이 가능하며,
 이것은 coroutineScope() 함수가 제공합니다.

 이런식으로 (=이렇게 구조화되게 코루틴을 별도 함수로 작성한다면)
 만약 concurrentSum() 코드블록 내 예외가 발생된다면, 해당 scope 내에서 실행되어진 모든 코루틴은 취소됩니다.

 */