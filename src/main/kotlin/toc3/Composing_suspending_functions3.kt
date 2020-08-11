package toc3

import kotlinx.coroutines.*
import kotlin.system.measureTimeMillis

//suspend fun doSomethingUsefulOne(): Int {
//    delay(1000L) // pretend we are doing something useful here
//    return 13
//}
//
//suspend fun doSomethingUsefulTwo(): Int {
//    delay(1000L) // pretend we are doing something useful here, too
//    return 29
//}

fun main() = runBlocking {
    val time = measureTimeMillis {
        val one = async(start = CoroutineStart.LAZY) { doSomethingUsefulOne() }
        val two = async(start = CoroutineStart.LAZY) { doSomethingUsefulTwo() }
        // some computation
        one.start() // start the first one
        two.start() // start the second one
        println("The answer is ${one.await() + two.await()}")
    }
    println("Completed in $time ms")
}

/**
 * Lazily started async
 *

 추가적으로, async 함수는 start 파라미터로 CoroutineStart.LAZY 을 전달하여
 지연 실행이 가능합니다. (=비동기 처리 시작 요청이 반드시 되어야 실행됩니다.)
 이 모드(LAZY) 에서는, 코루틴의 결과를 필요로 하는 시점에 await() 를 호출하거나,
 start() 를 통해 지연 시작이 가능합니다.

 따라서, 여기 두 코루틴은 앞의 예와 같이 바로 실행되지는 않지만, start() 호출은
 개발자가 원하는 시점에 가능합니다. 먼저 one 을 실행시키고, 다음에 two 를 실행시키며,
 각각의 코루틴 결과가 종료될 때 까지 대기하게 됩니다.

 만약 위 코드에서 먼저 start() 를 시작하지 않고 println 에서 await() 만 호출할 경우,
 순차적으로 동작될 것이므로 이는 lazy을 의도하고 사용된 사례가 아니라고 할 수 있습니다.
 (= lazy start 를 의도하지 않고 사용했다는 의미)
 async(start = CoroutineStart.LAZY) 의 경우, 연산 수행 결과를 중단함수(suspending function)
 에서 전달하는 케이스가 아니라면 lazy 로 사용케이스를 대체할 수 있다.
 */