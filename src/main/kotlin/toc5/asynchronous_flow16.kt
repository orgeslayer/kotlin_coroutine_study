package toc5

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import kotlin.system.measureTimeMillis

fun simpleBuffering(): Flow<Int> = flow {
    for (i in 1..3) {
        delay(100) // pretend we are asynchronously waiting 100 ms
        emit(i) // emit next value
    }
}

fun main() = runBlocking {
    val time = measureTimeMillis {
        simpleBuffering().collect { value ->
            delay(300) // pretend we are processing it for 300 ms
            println(value)
        }
    }
    println("Collected in $time ms")
}

/**
 * Buffering (1)
 *

 flow 에서 장시간 수행되는 동작에 대하여 별도 코루틴을 실행시키는 것은 특히
 flow 를 수집(=collect)하는 동작의 전체적인 시간 관점에서 도움이 될 수 있습니다.
 예를들어, simpleBuffering() 에서 방출(emission) 이 느리고, 요소들을 생산하는데
 100ms 가 소요되며, 각 요소를 수집(collect) 하는 부분도 300ms 걸리는 것을 생각해 봅시다.
 이러한 flow 가 3개의 숫자를 collect 하는데 얼마나 시간이 소요되는지 확인해 봅시다.

 ------------
    1
    2
    3
    Collected in 1226 ms
 ------------

 약 1200ms (3개의 숫자이며 각각 400ms가 소요) 가 전체 수집에 시간이 소요되는 것을 확인할 수 있습니다.

 */