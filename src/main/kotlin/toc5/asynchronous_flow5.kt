package toc5

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

fun flowColdSimple(): Flow<Int> = flow {
    println("Flow started")
    for(i in 1..3) {
        delay(100) // pretend we are doing something useful here
        emit(i) // emit next value
    }
}

fun main() = runBlocking {
    println("Calling simple function...")
    val flowColdSimple = flowColdSimple()
    println("Calling collect...")
    flowColdSimple.collect { value -> println(value) }
    println("Calling collect again...")
    flowColdSimple.collect { value -> println(value) }
}

/**
 * Representing multiple values
 * - Flows are cold
 *

 Flow는 시퀀스처럼 코드 스트림입니다. - flow{} 빌더 내부의 코드블록은
 flow가 수집될 때 까지 실행되지 않습니다. 이것은 다음 예제를 확인해보면 명확해 집니다.

 ----------------------------------------
    Calling simple function...
    Calling collect...
    Flow started
    1
    2
    3
    Calling collect again...
    Flow started
    1
    2
    3
 ----------------------------------------

 주요 이유는 (flow 를 리턴하는)예제 함수는 suspend 식별자가 마킹되어있지 않기 때문입니다.
 flowColdSimple() 함수 호출 즉시 결과를 반환하며, 아무것도 대기하지 않습니다.
 flow는 collect 함수 호출 때마다 매번 시작되며, 그것이 예제에서 "Flow started" 출력을
 collect 함수 호출 시마다 확인하게 되는 이유입니다.

 */