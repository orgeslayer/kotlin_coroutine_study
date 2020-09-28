package toc5

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import kotlin.system.measureTimeMillis

fun simpleFlowException(): Flow<Int> = flow {
    for (i in 1..3) {
        println("Emitting $i")
        emit(i) // emit next value
    }
}


fun main() = runBlocking {
    try {
        simpleFlowException().collect { value ->
            println(value)
            check(value <= 1) { "Collected $value" }
        }
    } catch (e: Throwable) {
        println("Caught $e")
    }
}

/**
 * Flow exceptions
 * - flow 컬렉션은 배출자(emitter) 혹은 연산자(operator) 코드블록에서 예외 발생시
 * 예외와 함께 종료될 수 있습니다. 이러한 예외들을 처리하는 몇 가지 방법을 확인할 수 있습니다.
 *
 * - Collector try and catch
 *

 수집가는 코틀린의 try / catch 블록을 이용하여 예외 처리가 가능합니다.
 이 코드는 성공적으로 collect 종료 연산자에서 예외를 받아 처리하고,
 출력된 결과에서 보는 것 처럼 이 후에는 더이상 값이 방출되지 않습니다.

 ------------------------------------------------------------
    Emitting 1
    1
    Emitting 2
    2
    Caught java.lang.IllegalStateException: Collected 2
 ------------------------------------------------------------

 */