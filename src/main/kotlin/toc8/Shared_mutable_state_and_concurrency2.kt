package toc8

import kotlinx.coroutines.*
import kotlin.system.measureTimeMillis

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

@Volatile
var volatileCounter = 0

fun main() = runBlocking {
    withContext(Dispatchers.Default) {
        massiveRun {
            volatileCounter++
        }
    }
    println("Counter = $volatileCounter")
}

/**
 * Volatiles are of no help
 *

 단순히 변수에 Volatile 을 지정하는 것 만으로도 동시성 문제를 해결할 수 있다는 오해가 있습니다.
 앞 예제의 counter 변수에서 Volatile 을 적용하고 다시 실행해봅시다.

 이 예제는 속도는 더 느려지며, 여전히 종료 후 "Counter = 100000" 을 출력하지 않습니다.
 왜냐면 volatile 변수는 선형적인('원자성'의 기술적 용어) 읽기 및 쓰기를 보장하지만,
 더 큰 액션에 대한 원자성을 제공하지는 않기 때문입니다. (예제에서는 값을 증가시키는 것)

 */