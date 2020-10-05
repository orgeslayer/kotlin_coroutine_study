package toc5

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import kotlin.system.measureTimeMillis

fun main() = runBlocking {
    val startTime = System.currentTimeMillis() // remember the start time
    (1..3).asFlow().onEach { delay(100) } // a number every 100 ms
            .flatMapMerge { requestFlow(it) } // see asynchronous_flow23.kt
            .collect { value -> // collect and print
                println("$value at ${System.currentTimeMillis() - startTime} ms from start")
            }
}

/**
 * Flattening flows
 * - flatMapMerge
 *

 다른 플래트닝 방법은(=mode) 가능한 빠르게 단일 flow 에서 방출된(emitted) flow 들을
 비동기로 수집하여 처리하는 것입니다. 이것은 flatMapMerge 와 flattenMerge 연산자를
 구현하고 있습니다. 두 가지 연산자 모두 한번에 수집하려는 flow 의 동작들의 수를 제한할 수 있는
 파라미터를 선택적으로(=optional) 지정할 수 있습니다.(기본적으로, DEFAULT_CONCURRENCY 와 동일합니다.)

 ---------------------------------------------
    1: First at 154 ms from start
    2: First at 247 ms from start
    3: First at 353 ms from start
    1: Second at 655 ms from start
    2: Second at 752 ms from start
    3: Second at 859 ms from start
 ---------------------------------------------

 flatMapMerge 의 동시성 특징은 출력결과에서 명확히 드러납니다.

 Note) flatMapMerge 는 자신의 코드블록 (예제에서는 { requestFlow(it) })은 순차적으로 수집하지만,
 각 flow의 결과는 비동기적으로 수집됩니다. 이것은 순차으로 map { requestFlow(it) } 을 호출하고,
 그 결과를 flattenMerge 을 수행하는 것과 동일합니다.

 */