package toc8

import kotlinx.coroutines.*
import kotlinx.coroutines.sync.*

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

val mutex = Mutex()
var mutexCounter = 0

fun main() = runBlocking {
    withContext(Dispatchers.Default) {
        massiveRun {
            // protect each increment with lock
            mutex.withLock {
                mutexCounter++
            }
        }
    }
    println("Counter = $mutexCounter")
}

/**
 * Mutual exclusion
 *

 상호 배제 방법은 공유 상태의 모든 수정을 임계영역(=critical section)으로 절때 비동기적으로
 실행되지 않도록 보호하는 해결책입니다. 블로킹 환경에서는 일반적으로 synchronized 또는 ReentrantLock
 을 사용합니다. 코루틴에서는 이러한 해결책의 대안으로 Mutex 가 있습니다. lock / unlock 함수로
 임계영역(=critical section) 범위를 정할 수 있습니다. 중요한 차이점은, Mutex.lock() 은
 중단함수로, 스레드를 블로킹 하지 않습니다.

 또한 Mutex 는 withLock 확장 함수를 제공하여 아래 패턴을 편리하게 작성 가능합니다 :
  mutex.lock()
  try {
    ...
  } finally {
    mutex.unlock()
  }

 이 예제에서는 상호배재를 세밀하게 제어하고있어 그만큼 대가가 필요합니다.
 그러나 몇몇 공유 상태를 주기적으로 변경해야 하고, 이를 위한 스레드 한정을 적용하기
 어려운 경우에는 좋은 해결책이 될 수 있습니다.
 */