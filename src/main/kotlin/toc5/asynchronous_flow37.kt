package toc5

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

fun foo(): Flow<Int> = flow {
    for (i in 1..5) {
        println("Emitting $i")
        emit(i)
    }
}

fun main() = runBlocking {
    foo().collect { value ->
        if (value == 3) cancel()
        println(value)
    }
}

/**
 * Flow cancellation checks (1)
 *

 flow 빌더는 편의를 위하여 ensureActive 를 통해 각 방출되어지는 값에 대한 취소 여부 체크를 수행합니다.
 이것은 flow {...} 에서 방출되는 루프가 취소 가능하다는 것을 의미합니다:
 예제코드에서 3개의 숫자가 수집되는 것을 확인하고,
 4번째 숫자를 방출할 때 CancellationException 이 발생되는 것을 확인할 수 있습니다.

 ----------------------------------------
    Emitting 1
    1
    Emitting 2
    2
    Emitting 3
    3
    Emitting 4
    Exception in thread "main" kotlinx.coroutines.JobCancellationException: BlockingCoroutine was cancelled; job=BlockingCoroutine{Cancelled}@de0a01f
 ----------------------------------------

 */