package toc5

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

fun log(msg: String) = println("[${Thread.currentThread().name}] $msg")

fun simpleContext() = flow {
    log("Started simple flow")
    for (i in 1..3) {
        emit(i)
    }
}

fun main() = runBlocking {
    simpleContext().collect { value -> log("Collected $value") }
}

/**
 * Flow context
 *

 flow 에서 수집동작(collect)은 항상 호출된 코루틴의 컨텍스트에서 발생됩니다
 아래와 같은 코드를 예를들면, simple() flow 구현 세부사항과 관계없이
 코드 작성자가 지정한 컨텍스트에서 코드가 수행됩니다.

 --------------------------------------------------------------------
    withContext(context) {
        simple().collect { value ->
            println(value) // run in the specified context
        }
    }
 --------------------------------------------------------------------

 이러한 flow 특징을 컨텍스트 보존(context preservation) 이라고 부릅니다.

 따라서, 기본적으로 flow{...} 빌더의 내부코드는 플로우 수집을 요청한 (=collector)
 코루틴의 컨텍스트에서 실행됩니다. 예를들어 호출한 스레드를 출력하면서, 3개의 숫자를 출력(emit)하는
 함수를 생각해 봅시다. 예제코드를 실행시키면 다음과 같습니다.

 --------------------------------------------------------------------
    [main @coroutine#1] Started simple flow
    [main @coroutine#1] Collected 1
    [main @coroutine#1] Collected 2
    [main @coroutine#1] Collected 3
 --------------------------------------------------------------------

 simpleContext() 을 메인스레드에서부터 호출되므로, simpleContext() flow 의 body (구현)
 또한 메인스레드에서 호출됩니다. 이것이 빠르게 동작되는 것을 보장하고, 호출자를 블록하지 않고 실행되는 컨텍스트를
 고려하지 않는 비동기 처리를 위한 기본 방법입니다.

 ---------------------------
 * 위 예제는 스레드 및 코루틴 컨텍스트 확인도 필요하므로 실행 시 디버그모드로 실행해 봐야 합니다.
   - '-ea' 옵션 사용

 */