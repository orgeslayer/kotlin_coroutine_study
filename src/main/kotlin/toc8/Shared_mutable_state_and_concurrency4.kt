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

val counterContext = newSingleThreadContext("CounterContext")
var defaultCounter = 0

fun main() = runBlocking {
    withContext(Dispatchers.Default) {
        massiveRun {
            // confine each increment to a single-threaded context
            withContext(counterContext) {
                defaultCounter++
            }
        }
    }
    println("Counter = $defaultCounter")
}

/**
 * Thread confinement fine-grained
 *

 스레드 한정(Thread confinemnet) 은 변경 가능한 공유 상태에 대한 모든 접근을
 단일 스레드에서만 처리하도록 제한하여 공유 상태 문제에 대한 해결 접근 방식입니다.
 UI 상태가 단일 이벤트 디스패치 / 어플리케이션 스레드로 제한되는 UI 응용 프로그램에서 보통 사용됩니다.
 코루틴에서는 단일 스레드 컨텍스트를 사용하는 방법으로 쉽게 적용할 수 있습니다.

 이 예제코드는 스레드 한정 코드블록을 세밀하게 분류하기 때문에 매우 느리게 동작합니다.
 각각의 증가 처리는 다중 스레드 컨텍스트 (Dispatchers.DEFAULT) 에서 싱글 스레드 스레드 컨텍스트로
 전환이 발생하며 이루어집니다.

 ----------------------------------------
 * Thread Confinement 에 대한 이야기는 아래 링크를 참조하세요.
 > http://egloos.zum.com/aeternum/v/1262507

 */