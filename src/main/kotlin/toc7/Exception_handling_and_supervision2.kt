package toc7

import kotlinx.coroutines.*

fun main() = runBlocking {
    val handler = CoroutineExceptionHandler { _, exception ->
        println("CoroutineExceptionHandler got $exception")
    }
    val job = GlobalScope.launch(handler) { // root coroutine, running in GlobalScope
        throw AssertionError()
    }
    val deferred = GlobalScope.async(handler) { // also root, but async instead of launch
        throw ArithmeticException() // Nothing will be printed, relying on user to call deferred.await()
    }
    joinAll(job, deferred)
}

/**
 * - CoroutineExceptionHandler
 *

 예외 핸들러를 커스터마이징함으로써 콘솔에서 표시되지 않은, 잡히지 않은 예외 출력에 대하여 처리 가능합니다.
 CoroutineExceptionHandler 라는 루트 코루틴의 컨텍스트 요소는
 루트 코루틴과 전체 자식 코루틴에서 예외가 발생될 때 catch 와 같이 범용적인 코드블록 사용이 가능합니다.
 이것은 Thread.uncaughtExceptionHandler 와 유사합니다. 코루틴은 예외 처리 핸들러가 호출될 때,
 이미 종료 되었다는 것을 알 수 있습니다. 일반적으로 핸들러는 로그를 출력하거나,
 몇 가지 에러를 표시하거나, 어플리케이션을 종료하거나 다시 시작하기 위하여 사용합니다.

 JVM 에서는 CoroutineExceptionHandler를 글로벌하게 예외 처리한 것을 재정의하여
 ServiceLoader 를 통하여 등록이 가능합니다. 이는 특별한 예외 처리를 수행하는 핸들러가 등록되어있지
 않을 경우 수행되는 Thread.defaultUncaughExceptionHandler 와 유사합니다.
 안드로이드에서는 기본적으로 코루틴 글로벌 예외처리 핸들러로 unCaughtExceptionPreHandler 가
 내장되어있습니다. (=installed)

 CoroutineExceptionHandler 는 일반적으로 잡히지 않는(=검색되지 않는) - 별도 처리되지 않은 예외상황에서
 호출됩니다. 특히, 모든 자식 코루틴(다른 Job의 context에서 생성된)은 그 부모 코루틴에게 예외 처리하는 것을
 위임하며, 그 부모 코루틴 역시 부모 코루틴에게 위임하며, 결국 최상위 코루틴(root)까지 위임됩니다.
 따라서, context 에 설정된 CoroutineExceptionHandler 는 절대 사용되지 않습니다.

 Note) 감시되는 범위에서 실행되는 코루틴은 부모에게 예외를 전파하지 않으며 이 규칙에서 제외됩니다.
 Suervision 섹션에서 좀 더 자세한 내용을 다룹니다.

 이 예제코드는 다음과 같이 출력됩니다.

 ----------------------------------------
    CoroutineExceptionHandler got java.lang.AssertionError
 ----------------------------------------


 */