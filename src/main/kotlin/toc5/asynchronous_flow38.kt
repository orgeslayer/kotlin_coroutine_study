package toc5

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

fun main() = runBlocking {
    (1..5).asFlow().collect { value ->
        if (value == 3) cancel()
        println(value)
    }
}

/**
 * Flow cancellation checks (2)
 *

 하지만, 대부분의 다른 flow 연산자들은 성능상의 이유로 스스로 추가적인 취소 여부 체크를
 수행하지 않습니다. 예를들어, 만약 IntRange.asFlow 확장함수로 동일한 반복 작업을 수행하고
 아무 곳에서도 일시중단하지 않을 경우, 취소에 대하여 확인을 하지 않습니다.

 예제코드 실행결과, 1부터 5까지 모든 숫자가 수집 후 runBlocking 에서 결과를 반환하기 전에
 취소가 runBlocking 에서 감지됩니다.

 ----------------------------------------
    1
    2
    3
    4
    5
    Exception in thread "main" kotlinx.coroutines.JobCancellationException: BlockingCoroutine was cancelled;
        job=BlockingCoroutine{Cancelled}@5ecddf8f
 ----------------------------------------
 */