package toc5

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import java.lang.RuntimeException

//fun main() = runBlocking {
//    simpleFinally()
//            .onCompletion { println("Done") }
//            .collect { value -> println(value) }
//}

fun simpleException() = flow {
    emit(1)
    throw RuntimeException()
}

fun main() = runBlocking {
    simpleException()
        .onCompletion { cause -> if (cause != null) println("Flow completed exceptionally") }
        .catch { cause -> println("Caught exception") }
        .collect { value -> println(value) }
}

/**
 * Flow completion
 * - Declarative handling
 *

 종료처리에 대하여 선언적으로 접근해 본다면, flow 가 수집이 완전히 종료되었을 때 호출되는
 onCompletion 중간 연산자가 있습니다. 이전의 예제를 onCompletion 을 이용할 경우
 동일하게 출력되게 변경이 가능합니다.

 * 현재 파일 최상단 주석 코드를 확인 해 보세요.

 onCompletion 의 주요 장점은 정상적으로 종료 되었거나, 예외적으로 종료된 것에 대하여
 판단을 할 수 있는 lambda 로부터 전달되는 Throwable 파라미터 입니다. 이 예제에서는
 flow 에서 숫자 1을 방출하고 이 후에 예외상황을 발생시킵니다.

 예상 되는 것 처럼, 아래와 같이 출력됩니다.

 -------------------------------------
    1
    Flow completed exceptionally
    Caught exception
 -------------------------------------

 onCompletion 연산자는 catch 연산자와 다르게, 예외상항을 처리하지 않습니다.
 예제코드에서 알 수 있듯, 예외는 여전히 하류(downstream) 로 전달됩니다.
 onCompletion 연산자 코드블록으로 예외는 전달되고, catch 연산자와 함께
 처리도 가능합니다.

 ------------------------------------------
 Note) 예제 코드에서는 onComplete, catch 연산자의 순서로 코드블록이 작성되었습니다.
 만약 catch, onComplete 로 순서를 변경한다면 어떻게 될까요? 실행 결과는 아래와 같습니다.
 (코드를 변경 후 실행 해 봅시다.)

 -------------------------------------
    1
    Caught exception
 -------------------------------------

 catch 연산자의 내부 구현체를 확인해 보세요.
 (cache 연산자의 Transparent 특성 - asynchronous_flow30.kt 참조)

 */