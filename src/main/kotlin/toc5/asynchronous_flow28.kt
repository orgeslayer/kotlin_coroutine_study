package toc5

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import kotlin.system.measureTimeMillis

fun simpleFlowExceptionEverything() = flow {
    for (i in 1..3) {
        println("Emitting $i")
        emit(i) // emit next value
    }
}.map { value ->
    check(value <= 1) { "Crashed on $value" }
    "string $value"
}


fun main() = runBlocking {
    try {
        simpleFlowExceptionEverything().collect { value -> println(value) }
    } catch (e: Throwable) {
        println("Caught $e")
    }
}

/**
 * Flow exceptions
 * - Everything is caught
 *

 앞의 예제는 방출자(emitter), 중간 혹은 종료 연산자에서 발생되는 어떠한 예외라도
 잡아내게 됩니다. 예를들어, 방출된 수를 문자열로 변환되지만, 변환 코드에서
 예외를 발생사도록 코드를 변경해 봅시다.

 이 예제는 Exception 에 의해 중단되고 수집활동은 중지 됩니다.
 ---------------------------------
    Emitting 1
    string 1
    Emitting 2
    Caught java.lang.IllegalStateException: Crashed on 2
 ---------------------------------

 */