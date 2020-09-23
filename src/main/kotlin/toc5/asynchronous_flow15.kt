package toc5

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

fun simpleFlowOn() = flow {
    for (i in 1..3) {
        Thread.sleep(100) // pretend we are computing it in CPU-consuming way
        log("Emitting $i")
        emit(i) // emit next value
    }
}.flowOn(Dispatchers.Default)

fun main() = runBlocking {
    simpleFlowOn().collect { value -> log("Collected $value") }
}

/**
 * Flow context
 * - flowOn operator
 *

 예외 메시지(=앞전 이슈에서 에러 메시지 참조)에서 flow 실행을 위한(=emission 가능하기 위한)
 컨텍스트를 변경하기 위하여 flowOn 을 알려주고 있습니다. 컨텍스트를 올바르게 변경하는 예제는
 이번에 확인이 가능하며, 또한 실행되는 스레드명을 출력하여 어떻게 모든 것이 정상적으로 동작되는지 확인할 수 있습니다.

 --------------------------------------------------
    [DefaultDispatcher-worker-1 @coroutine#2] Emitting 1
    [main @coroutine#1] Collected 1
    [DefaultDispatcher-worker-1 @coroutine#2] Emitting 2
    [main @coroutine#1] Collected 2
    [DefaultDispatcher-worker-1 @coroutine#2] Emitting 3
    [main @coroutine#1] Collected 3
 --------------------------------------------------

 flow {...} 은 백그라운드 스레드에서 동작하며, 수집(=collect)은 메인스레드에서 동작하는 것을 명심하세요.

 또 한가지 눈여겨볼 부분은 flowOn 연산자가 flow 의 순차적 특성을 변경했다는 것입니다.
 collect 이 하나의 코루틴에서 발생되고(coroutin#1) flow 값 반환의 경우에는
 다른 스레드에서 실행되는 코루틴(coroutine#2)에서 발생됩니다.
 flowOn 연산자는 컨텍스트에서 CoroutineDispatcher 를 변경해야 할 때
 값을 상류(=upstream)로 전달하기위한 또다른 코루틴을 생성한다.
 */