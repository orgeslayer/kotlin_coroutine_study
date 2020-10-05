package toc5

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

//fun main() = runBlocking {
//    (1..5).asFlow()
//        .onEach { currentCoroutineContext().ensureActive() }
//        .collect { value ->
//            if (value == 3) cancel()
//            println(value)
//        }
//}

fun main() = runBlocking {
    (1..5).asFlow().cancellable().collect { value ->
        if (value == 3) cancel()
        println(value)
    }
}

/**
 * Flow cancellation checks
 * - Making busy flow cancellable
 *

 (이전 예제 처럼) 코루틴으로 사용중인 루프가 있는 경우 명시적으로 취소를 체크해야 합니다.
 .onEach { currentCoroutineContext().ensureActive() } 를 추가할 수 있지만,
 다음과 같은 작업을 위하여 cancellable 연산자를 제공할 준비가 되어 있습니다.

 예제에서 cancellable 연산자를 추가하여 실행할 경우, 1부터 3까지의 숫자만 수집되어집니다 :

 --------------------------------------------------
    1
    2
    3
    Exception in thread "main" kotlinx.coroutines.JobCancellationException: BlockingCoroutine was cancelled;
        job=BlockingCoroutine{Cancelled}@5479e3f
 --------------------------------------------------

 */