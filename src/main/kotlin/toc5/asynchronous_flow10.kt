package toc5

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

fun numbers(): Flow<Int> = flow {
    try {
        emit(1)
        emit(2)
        println("This line will not execute")
        emit(3)
//    } catch (e: Exception) {
//        println("Exception in numbers : ${e.localizedMessage}")
    } finally {
        println("Finally in numbers")
    }
}

fun main() = runBlocking {
    numbers()
        .take(2) // take only the first two
        .collect { value -> println(value)}

}

/**
 * Size-limiting operators
 *

 flow 에서 take 와 같은 크기를 제한하는 중간 연산자는 해당 한계에 도달했을 때 flow 실행을 취소합니다.
 코루틴에서 취소는 항상 예외를 발생시키므로 취소될 때에도 (try {...} finally {...} 와 같은 코드블록)
 자원 관리형식의 함수들 처리를 통해 정상적으로 동작할 수 있게 합니다.

 예제 코드에서는 flow 코드블록이 명확히 숫자가 두 번째 emit 되고 나서 실행이 멈추는 것을 확인할 수 있습니다.

 ------------------------------------------
    1
    2
    Finally in numbers
 ------------------------------------------

---------------------------------------------------
 * flow 에 take 를 설정할 경우, 취하려고하는 횟수만큼 emit 이 발생되면 즉시
    kotlinx.coroutines.flow.internal.AbortFlowException
   이 발생됩니다.

 */