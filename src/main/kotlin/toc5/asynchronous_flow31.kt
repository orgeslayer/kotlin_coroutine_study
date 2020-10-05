package toc5

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import kotlin.system.measureTimeMillis

fun main() = runBlocking {
    simpleExceptionTransparency()
        .onEach { value ->
            check(value <= 1) { "Collected $value" }
            println("onEach $value")
        }
        .catch { e -> println("Caught $e") }
        .collect()
//        .collect { collectValue ->
//            println("Collected $collectValue")
//        }
}

/**
 * Exception transparency
 * - Catching declaratively
 *

 모든 예외 상황에 대한 확인이 필요할 경우, collect 내부에서 처리하지 말고
 catch 연산자 앞 onEach 에 코드 블록을 두어 선언적으로 코드 작성이 가능합니다.
 이 flow 에서 수집에 특별한 파라미터 없이 collect() 를 호출함으로써
 트리거가 되어야 합니다.

 이제 try/catch 코드 블록을 명시적으로 사용하지 않아도 예외를 잡아서
 "Caught ..." 가 출력되는 것을 확인할 수 있습니다:

 ----------------------------------------
    Emitting 1
    onEach 1
    Emitting 2
    Caught java.lang.IllegalStateException: Collected 2
 ----------------------------------------

 */