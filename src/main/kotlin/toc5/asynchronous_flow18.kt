package toc5

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import kotlin.system.measureTimeMillis

fun simpleBufferingConflation(): Flow<Int> = flow {
    for (i in 1..3) {
        delay(100) // pretend we are asynchronously waiting 100 ms
        emit(i) // emit next value
    }
}

fun main() = runBlocking {
    val time = measureTimeMillis {
        simpleBufferingConflation()
            .conflate() // conflate emissions, don't process each one
            .collect { value ->
                delay(300) // pretend we are processing it for 300 ms
                println(value)
            }
    }
    println("Collected in $time ms")
}

/**
 * Buffering
 * - Conflation
 *

 flow 에서 연산의 일부분이나 연산 상태의 업데이트 될 때 각각의 값을 처리하는 것은 불필요하며,
 가장 최근의 결과값만 처리가 필요할 수 있습니다. 이러한 경우, conflate 연산자를 사용하여
 collect 처리가 너무 느릴 경우 수집된 결과 (=emited value) 중간 값들을 스킵할 수 있습니다.
 이전 예제를 기반으로 다음 예제를 확인 해 봅시다:

 ----------------------------------------------------
    1
    3
    Collected in 753 ms
 ----------------------------------------------------

 첫 번째 번호가 아직 처리 중인데 flow 에서 이미 세 번째 숫자가 생성되어 두번째 값은 스킵되고,
 가장 최근의 값인 세 번째 값이 collect 에서 수집됩니다.

 ----------------------------------------
 * conflation > 융합, 합성, 해당 문서에서는 병합 이라고 해석되어도 좋습니다.

 */