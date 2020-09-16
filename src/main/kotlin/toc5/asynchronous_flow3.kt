package toc5

import kotlinx.coroutines.*

suspend fun suspendingSimple(): List<Int> {
    delay(1000) // pretend we are doing something asynchronous here
    return listOf(1, 2, 3)
}

fun main() = runBlocking {
    suspendingSimple().forEach { value -> println(value) }
}

/**
 * Representing multiple values
 * - Suspending functions
 *

 하지만 이전 예제코드(=asynchronous_flow2.kt 예제 참조)의 코드블록은
 메인스레드를 실행하고있는 코드블록을 정지 시킵니다.
 결과값을 list로 반환하는데 코드블록 수행하는데 정지(=stop)가 없도록
 suspend 식별자를 suspendingSimple 함수 앞에 추가함으로써 리턴할 수 있습니다.

 이 예제코드는 몇 초 후에 숫자값들을 출력하게 됩니다.

 */