package toc5

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

fun simpleWrong() = flow {
    // The WRONG way to change context for CPU-consuming code in flow builder
    withContext(Dispatchers.Default) {
        for (i in 1..3) {
            Thread.sleep(100) // pretend we are computing it in CPU-consuming way
            emit(i) // emit next value
        }
    }
}

fun main() = runBlocking {
    simpleWrong().collect { value -> println(value) }
}

/**
 * Flow context
 * - Wrong emission withContext
 *

 그러나, 장시간 실행으로 CPU 소모를 하는 코드의 경우 Dispatchers.DEFAULT
 컨텍스트에서 수행되어야 할 수 있으며, 화면 갱신을 하는 코드의 경우 Dispatchers.Main 컨텍스트에서
 수행되어야 할 때도 있다. 일반적으로 withContext 는 코틀린 코루틴을의 컨텍스트를 변경하는데 사용되지만,
 flow {...} 빌더의 코드일 경우 컨텍스트 보존법칙(=context preservation)을 따르기 때문에,
 다른 컨텍스트에서 emit 을 허용하지 않습니다.

 예제 코드를 실행하면 다음과 같은 예외가 발생됩니다.

 ---------------------------------------------------------------
    Exception in thread "main" java.lang.IllegalStateException: Flow invariant is violated:
        Flow was collected in [BlockingCoroutine{Active}@6ddb07a, BlockingEventLoop@3664a205],
        but emission happened in [DispatchedCoroutine{Active}@6d3e070b, DefaultDispatcher].
        Please refer to 'flow' documentation or use 'flowOn' instead
    at ...
 ---------------------------------------------------------------

 */