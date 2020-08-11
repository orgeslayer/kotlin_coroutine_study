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

suspend fun failedConcurrentSum(): Int = coroutineScope {
    val one = async<Int> {
        try {
            delay(Long.MAX_VALUE) // Emulates very long computation
            42
        } finally {
            println("First child was cancelled")
        }
    }
    val two = async<Int> {
        println("Second child throws an exception")
        throw ArithmeticException()
    }
    one.await() + two.await()
}

fun main() = runBlocking<Unit> {
    try {
        failedConcurrentSum()
    } catch (e: ArithmeticException) {
        println("Computation failed with ArithmeticException")
    }
}

/**
 * Structured concurrency with async (2)
 *

 코루틴 취소 역시 코루틴 계층 (=구조화된 코루틴)을 따라 전파됩니다.

 상위 루틴(메인 함수)이 자식 루틴(예제에서는 two{ })이 취소 혹은 실패됨에 따라,
 나머지 자식 코루틴이 취소되고, 그 후에 부모 코루틴이 취소 되는 것을 확인할 수 있습니다.

 ---------------------------------------------------
    Second child throws an exception
    First child was cancelled
    Computation failed with ArithmeticException
 ---------------------------------------------------
 */