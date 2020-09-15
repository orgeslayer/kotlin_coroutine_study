package toc4

import kotlinx.coroutines.*

fun main() = runBlocking {
    log("Started main coroutine")
    // run two background value computations
    val v1 = async(CoroutineName("v1coroutine")) {
        delay(500)
        log("Computing v1")
        252
    }
    val v2 = async(CoroutineName("v2coroutine")) {
        delay(1000)
        log("Computing v2")
        6
    }
    log("The answer for v1 / v2 = ${v1.await() / v2.await()}")
}

/**
 * Naming coroutines for debugging
 *

 자동으로 할당된 ID는 동일한 코루틴에서 발생한 로그인지 확인하는 작업에서는 종종 쓸만합니다.
 하지만, 특정 요청의 처리 작업이나 백그라운드 작업을 특정하는 태스크를 수행한다면,
 디버깅을 위해 명시적으로 코루틴 이름을 지정하는 것이 낫습니다.
 CoroutineName 이라는 컨텍스트 요소는 스레드 이름과 동일한 목적으로 기능이 제공됩니다.
 디버깅 모드이면 CoroutineName 은 코루틴을 수행중인 스레드 이름과 함께 나타나게 됩니다.

 다음 예제 코드는 이러한 개념을 확인할 수 있습니다.
 예제 코드를 -Dkotlinx.coroutines.debug JVM 옵션을 활용하여 실행하면 다음과 같은 출력 결과를 확인할 수 있습니다.

 --------------------------------------------------
    [main @coroutine#1] Started main coroutine
    [main @v1coroutine#2] Computing v1
    [main @v2coroutine#3] Computing v2
    [main @coroutine#1] The answer for v1 / v2 = 42
 --------------------------------------------------

 */


