package toc5

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import kotlin.system.measureTimeMillis

fun main() = runBlocking {
    val startTime = System.currentTimeMillis() // remember the start time
    (1..3).asFlow().onEach { delay(100) } // a number every 100 ms
            .flatMapLatest { requestFlow(it) } // see asynchronous_flow23.kt
            .collect { value -> // collect and print
                println("$value at ${System.currentTimeMillis() - startTime} ms from start")
            }
}

/**
 * Flattening flows
 * - flatMapLatest
 *

 "최신 값 처리(Asynchronous_flow19.kt 참조)" 섹션에서 확인한
 collectLatest 와 유사하게 새로운 flow 가 방출 되었을 때 이전의 flow 를 취소하고
 "최신의" 플래트닝 방법도(=mode) 있습니다. 그것은 flatMapLatest 연산자를 구현하고 있습니다.
 이 예제의 출력 결과는 flatMapLatest 의 동작 방식을 잘 보여줍니다.

 ----------------------------------------
    1: First at 151 ms from start
    2: First at 261 ms from start
    3: First at 362 ms from start
    3: Second at 862 ms from start
 ----------------------------------------

 Note) flatMapLatest 는 새로운 값을 받았을 때 코드블록 (예제에서는 { requestFlow(it) })
 을 취소시킵니다. requestFlow 호출 자체는 빠르고, 중단되지 않으며 취소도 되지 않아 이 예제에서는
 특별한 차이점이 없습니다. 하지만, 이 부분에서 delay 같은 중단함수를 사용한다면 flatMapLatest
 의 특징을 좀 더 명확하게 확인할 수 있습니다.
 */