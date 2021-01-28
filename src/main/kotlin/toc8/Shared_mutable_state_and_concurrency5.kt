package toc8

import kotlinx.coroutines.*

//suspend fun massiveRun(action: suspend () -> Unit) {
//    val n = 100  // number of coroutines to launch
//    val k = 1000 // times an action is repeated by each coroutine
//    val time = measureTimeMillis {
//        coroutineScope { // scope for coroutines
//            repeat(n) {
//                launch {
//                    repeat(k) { action() }
//                }
//            }
//        }
//    }
//    println("Completed ${n * k} actions in $time ms")
//}

//val counterContext = newSingleThreadContext("CounterContext")
//var defaultCounter = 0

fun main() = runBlocking {
    // confine everything to a single-threaded context
    withContext(counterContext) {
        massiveRun {
            defaultCounter++
        }
    }
    println("Counter = $defaultCounter")
}

/**
 * Thread confinement coarse-grained
 *

 실제로는 스레드 한정은 매우 큰 작업 단위로 이루어닙니다.
 예를들어, 큰 작업의 비즈니스 로직으로 상태를 업데이트하는 수행하는 것은 단일 스레드에 국한됩니다.
 다음 예제는 단일 스레드 컨텍스트에서 각 코루틴을 실행합니다.

 이제 훨씬 더 빨리 동작되며 정확한 결과가 출력됩니다.

 -----------------------------------------
 * `Volatiles are of no help` 와 함께 확인하면 Context 차이 이해에 도움이 됩니다.

 */