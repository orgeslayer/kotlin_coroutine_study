package toc2

import kotlinx.coroutines.*

fun main() = runBlocking {
    withTimeout(1300L) {
        repeat(1000) { i ->
            println("I'm sleeping $i ...")
            delay(500L)
        }
    }
}

/**
 * Timeout
 *

 코루틴의 실행을 취소하는 실질적인 이유는 그 수행시간이 일정 시간을 초대한 경우입니다.
 (=일반적으로 어떤 작업을 취소시키는 이유는 그 실행 시간이 너무 길어져서 허용 시간을 넘어설 경우입니다.)
 Job에 대한 참조를 직접 다뤄서 별도의 코루틴을 실행(launch)하여 해당 루틴(서브루틴) 동작 중 취소가 가능하지만,
 withTimeout 함수를 이용하여 처리할 수 있다. 예제를 실행 시켜보면 아래와 같은 결과를 확인할 수 있다.

 -------------------------------------------

    I'm sleeping 0 ...
    I'm sleeping 1 ...
    I'm sleeping 2 ...
    Exception in thread "main" kotlinx.coroutines.TimeoutCancellationException:
        Timed out waiting for 1300 ms

 -------------------------------------------

 withTimeout 에 의해 발생되는 TimeoutCancellationException은
 CancellationException의 서브클래스 입니다.

 우리는 이제까지 콘솔에서 스택트레이스가 출력된 것을 본 적이 없습니다.
 왜냐하면, 메인루틴 안에서 취소된 코루틴이 CancellationException 이 발생될 경우,
 정상적으로 코루틴이 끝난 것으로 간주하기 때문입니다.
 하지만, 이 예제의 경우 withTimeout을 메인 함수에서 사용했습니다.

 -------------------------------------------
 참조) 'remove stack trace' region 코드를 실행시켜 보면
 스텍트레이스 관련 내용을 이해하는데 도움이 됩니다. (runBlocking 내 try / catch 에서 트레이스 발생)
 */

// region remove stack trace
// fun main() = runBlocking {
//    try {
//        withTimeout(1300L) {
//            try {
//                repeat(1000) { i ->
//                    println("I'm sleeping $i ...")
//                    delay(500L)
//                }
//            } catch (e: Exception) {
//                println("[Working] $e")
//            } finally {
//                println("[Working] finally")
//            }
//        }
//    } catch (e: Exception) {
//        println("[runBlocking] $e")
//    } finally {
//        println("[runBlocking] finally")
//    }
// }
// endregion

