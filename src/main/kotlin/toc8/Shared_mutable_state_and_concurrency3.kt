package toc8

import kotlinx.coroutines.*
import java.util.concurrent.atomic.AtomicInteger

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

val atomicCounter = AtomicInteger()

fun main() = runBlocking {
    withContext(Dispatchers.Default) {
        massiveRun {
            atomicCounter.incrementAndGet()
        }
    }
    println("Counter = $atomicCounter")
}

/**
 * Thread-safe data structures
 *

 스레드와 코루틴 모두에서 모든 동기화 처리에 있어, 일반적인 해결책은 공유 상태에서 수행될 수 있는
 연산에 대하여 스레드 안전한 데이터 구조(synchronized, linearizable, 혹은 atomic)
 를 사용하는 것입니다. counter 예제에서는 AtomicInteger 클래스의 incrementAndGet() 함수를
 사용할 수 있습니다.

 예제 코드는 앞에서 소개한 특별한 문제를 해결하는 가장 빠른 해결책입니다.
 단순 카운터나, 컬랙션, 큐 그리고 다른 표준 데이터 구조나
 다른 기본 연산자들에도 적용 가능한 해결방법 입니다. 하지만, 이 해결방법은
 복잡한 상태로 확장시키거나, 스레드 안전하게 오퍼레이션을 확장하는 것이 쉽지 않습니다.
 */