package toc5

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

fun simpleFlowCancellation(): Flow<Int> = flow {
    for (i in 1..10) {
        delay(500)
        println("Emitting $i")
        emit(i)
    }
}

fun main() = runBlocking {
    withTimeoutOrNull(1250) { // Timeout after 1250ms
        simpleFlowCancellation().collect { value -> println(value) }
    }
    println("Done")
}

/**
 * Flow cancellation basics
 *

 Flow 는 코루틴의 취소에 대하여 일반적으로 협력적입니다.
 flow collection 역시 취소 가능한 중단 함수(delay 와 같은)에서 취소 되었을 때
 취소 될 수 있습니다. 아래 예제에서는 timeoutOrNull 코드블록을 수행하는 중
 flow 가 취소되어 동작을 멈추는지 확인할 수 있습니다.

 예제 코드에서 simpleFlowCancellation 함수의 flow 가 출력 결과 2개의 숫자만 emit 되는 것에 주목해야 합니다.

 ----------------------------------------
    Emitting 1
    1
    Emitting 2
    2
    Done
 ----------------------------------------

 */