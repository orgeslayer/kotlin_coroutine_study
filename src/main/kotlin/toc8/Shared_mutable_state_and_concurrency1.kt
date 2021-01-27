package toc8

import kotlinx.coroutines.*
import kotlin.system.measureTimeMillis

suspend fun massiveRun(action: suspend () -> Unit) {
    val n = 100  // number of coroutines to launch
    val k = 1000 // times an action is repeated by each coroutine
    val time = measureTimeMillis {
        coroutineScope { // scope for coroutines
            repeat(n) {
                launch {
                    repeat(k) { action() }
                }
            }
        }
    }
    println("Completed ${n * k} actions in $time ms")
}

var counter = 0

fun main() = runBlocking {
    withContext(Dispatchers.Default) {
        massiveRun {
            counter++
        }
    }
    println("Counter = $counter")
}

/**
 * Shared Mutable State and Concurrency
 * - 코루틴은 Dispatchers.Default 처럼 멀티스레드 디스패처에 의하여 동시에 실행될 수 있습니다.
 *   그것은 동시성으로 인해 발생될 수 있는 모든 일반적인 문제점을 가지고 있습니다. 주요 이슈는
 *   변경 가능한 공유 자원에 대해 접근함으로써 발생되는 동시성 문제입니다. 코루틴 관점에서 이 문제에
 *   대한 몇 가지 해결방법은 멀티스레드 관점에서의 해결책들과 비슷하지만, 몇 가지 해결 방법은 코루틴
 *   고유의 방식으로 해결이 가능합니다.
 *
 * - The problem
 *

 먼저 동일한 동작을 천 번씩 실행하는 코루틴을 100번 실행시켜 봅시다. 또한, 추후 비교를 위하여 완료 시간을
 측정해 봅시다.

 Dispatchers.Default 를 이용하여 멀티 스레드 환경에서 코루틴간 공유되는 변수를 증가시키는 동작을 시작합니다.

 최종적으로 어떤 값을 출력하나요? 100개의 코루틴이 서로 동기화 없이 여러개의 스레드에서 동시에 카운터를
 증가시키기 때문에, "Counter = 100000" 이 출력될 가능성은 거의 없습니다.
 */