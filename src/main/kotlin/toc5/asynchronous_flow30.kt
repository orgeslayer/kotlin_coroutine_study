package toc5

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import kotlin.system.measureTimeMillis

fun simpleExceptionTransparency(): Flow<Int> = flow {
    for (i in 1..3) {
        println("Emitting $i")
        emit(i)
    }
}

fun main() = runBlocking {
    simpleExceptionTransparency()
            .catch { e -> println("Caught $e") } // does not catch downstream exceptions
            .collect { value ->
                check(value <= 1) { "Collected $value" }
                println(value)
            }
}

/**
 * Exception transparency
 * - Transparent catch
 *

 예외처리의 투명성을 갖고 있는 catch 중간 연산자는 업스트림시 발생되는 예외만 캐치합니다.
 (=catch 연산자 위의 모든 연산자를 의미합니다.)
 예제 코드는 collect {...} 안의 코드블록(catch 아래에 위치한)은 예외를 발생시키고 난 후
 탈출됩니다:

 ------------------------------------------------------
    Emitting 1
    1
    Emitting 2
    Exception in thread "main" java.lang.IllegalStateException: Collected 2
        at ...
 ------------------------------------------------------

 이 예제에서는 catch 연산자가 있음에도 불구하고, "Caught ..." 메시지는 출력되지 않습니다.

 ------------------------------------------------------
 *

 */