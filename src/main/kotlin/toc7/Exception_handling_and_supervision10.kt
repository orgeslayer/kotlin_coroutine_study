package toc7

import kotlinx.coroutines.*
import java.io.IOException
import java.lang.NullPointerException

fun main() = runBlocking {
    val handler = CoroutineExceptionHandler { _, exception ->
        println("CoroutineExceptionHandler got $exception")
    }
    supervisorScope {
        val child = launch(handler) {
            println("The child throws an exception")
            throw AssertionError()
        }
        println("The scope is completing")
    }
    println("The scope is completed")
}

/**
 * Supervision
 * - Exceptions in supervised coroutines
 *

 감독되는(supervision) 코루틴과 일반(regular) 코루틴에서 또 다른 중요한 차이점은 예외처리입니다.
 모든 자식 코루틴은 예외에 대하여 스스로 처리해야 합니다. 이러한 차이점은 자식 코루틴에서 예외가
 부모 코루틴으로 전파되지 않는 사실에서 비롯됩니다. 이것은 supervisorScope 안에서 직접 실행된 코루틴은
 루트 코루틴 스코프에서 설치한(=installed) CoroutineExceptionHandler 를 사용하게 된다는 의미입니다.
 (자세한 내용은 CoroutineExceptionHandler 을 참조하세요.)

 이 예제는 다음과 같이 출력됩니다.

 ----------------------------------------
    The child throws an exception
    CoroutineExceptionHandler got java.lang.AssertionError
    The scope is completing
    The scope is completed
 ----------------------------------------

 Note) supervisorScope 을 coroutineScope 으로 변경 후 실행 결과를 확인 해보세요.
 그리고 child 를 "The scope is completed" 전에 join() 후 확인해 보세요.

 */