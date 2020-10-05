package toc5

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import kotlin.system.measureTimeMillis

fun simpleBufferingCollectLatest(): Flow<Int> = flow {
    for (i in 1..3) {
        delay(100) // pretend we are asynchronously waiting 100 ms
        emit(i) // emit next value
    }
}

fun main() = runBlocking {
    val time = measureTimeMillis {
        simpleBufferingCollectLatest()
            .conflate() // conflate emissions, don't process each one
            .collectLatest { value -> // cancel & restart on the latest value
                println("Collecting $value")
                delay(300) // pretend we are processing it for 300 ms
                println("Done $value")
            }
    }
    println("Collected in $time ms")
}

/**
 * Buffering
 * - Processing the latest value
 *

 Conflation 은 방출자(=emitter) 와 수잡가(=collector) 모두 느릴 경우 속도 향상을 시키는 방법 중 하나입니다.
 그것은 방출된 결과값을 삭제하는(=dropping) 방법으로 동작됩니다. 다른 방법은 느린 수집가(=collector) 를 취소시키고
 다시 새로운 값이 방출되었을 때 재시작 하는 방법입니다. xxxLatest 연산자 패밀리는 xxx 연산자의 필수 로직을 수행하지만,
 새로운 값에 대하여 코드블록을 취소하는 경우도 있습니다. 이전 예제에서 conflate 연산자를 collectLatest 로 변경해봅시다:

 ------------------------------------------
    Collecting 1
    Collecting 2
    Collecting 3
    Done 3
 ------------------------------------------

 collectLatest 의 코드블록은 300ms가가 소모되지만, 새로운 값은 매 100ms 마다 방출되며,
 우리는 모든 코드블록이 실행되는 것을 확인할 수 있습니다. 하지만, 마지막 값에 대해서만 완료되는 것을 확인할 수 있습니다.

 */