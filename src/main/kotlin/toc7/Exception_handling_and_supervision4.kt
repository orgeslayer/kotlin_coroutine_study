package toc7

import kotlinx.coroutines.*

fun main() = runBlocking {
    val handler = CoroutineExceptionHandler { _, exception ->
        println("CoroutineExceptionHandler got $exception")
    }
    val job = GlobalScope.launch(handler) {
        launch { // the first child
            try {
                delay(Long.MAX_VALUE)
            } finally {
                withContext(NonCancellable) {
                    println("Children are cancelled, but exception is not handled until all children terminate")
                    delay(100)
                    println("The first child finished its non cancellable block")
                }
            }
        }
        launch { // the second child
            delay(10)
            println("Second child throws an exception")
            throw ArithmeticException()
        }
    }
    job.join()
}

/**
 * - Cancellation and exceptions (2)
 *

 만약 코루틴이 CancellationException 과 다른 예외상황이 발생된다면,
 해당 예외상황과 함께 부모 역시 취소됩니다. 이러한 동작은 재정의 될 수 없으며(=무시될 수 없으며),
 코루틴 계증간 구조화된 동시성을 안정적으로 제공합니다. CoroutineExceptionHandler 구현체는
 하위 코루틴에는 이용되지 않습니다.

 Note) 이 예제의 CoroutineExceptionHandler 는 항상 GlobalScope 에서 생성된
 코루틴에 설치되어 있습니다.(=installed) 메인 코루틴은 설치된 예외 핸들러에도 불구하고 예외적으로
 자식 코루틴이 예외에 의하여 종료되면 항상 취소되기 때문에, 주요 작업 실행사항을 코루틴 범위에
 CoroutineExceptionHandler 를 설치(=installed)하는 것은 타당하지 않습니다.

 근본적으로 발생된 예외사항은 모든 자식들이 종료되고 나서 그 부모만이 처리하며, 이러한 사항은
 다음 예제에서 확인할 수 있습니다.

 ----------------------------------------------------
    Second child throws an exception
    Children are cancelled, but exception is not handled until all children terminate
    The first child finished its non cancellable block
    CoroutineExceptionHandler got java.lang.ArithmeticException
 ----------------------------------------------------

 */