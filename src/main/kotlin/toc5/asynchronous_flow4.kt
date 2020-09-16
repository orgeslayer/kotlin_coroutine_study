package toc5

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

fun flowSimple(): Flow<Int> = flow {
    for(i in 1..3) {
//        Thread.sleep(100)
        delay(100) // pretend we are doing something useful here
        emit(i) // emit next value
    }
}

fun main() = runBlocking {
    // Launch a concurrent coroutine to check if the main thread is blocked
    launch {
        for(i in 1..3) {
            println("I'm not blocked $i")
            delay(100)
        }
    }
    // Collect the flow
    flowSimple().collect { value -> println(value) }
}

/**
 * Representing multiple values
 * - Flow
 *

 결과로 전달되는 타입을 List<Int> 을 사용한다는 것은, 모든 값(=List의 모든 요소)을
 한번에 반환한다는 의미입니다. 비동기적으로 연산이 수행된 결과값을 스트림으로 나타내려면,
 이전 예제코드(=asynchronous_flow2.kt 예제 참조) 에서 동기적으로 처리되었던 Sequence<Int>
 타입과 유사하게 Flow<Int> 타입을 사용할 수 있습니다.

 다음 예제코드는 메인스레드를 중단(blocking)하지 않고 각 숫자를 출력하기 위해 100ms 씩 대기합니다.
 메인 스레드에서 실행중인 별도의 코루틴에서 "I'm not blocked" 를 매 100ms 마다 출력함으로써
 해당 상황을 확인합니다. (=메인 스레드를 중단하지 않고 각 숫자 출력을 확인을 위함)

 ------------------------------------------------
    I'm not blocked 1
    1
    I'm not blocked 2
    2
    I'm not blocked 3
    3
 ------------------------------------------------

 앞선 예제코드들과 Flow 에서 다음과 같은 차이점에 주목하세요.
 - Flow 생성을 위하여 flow 빌더 함수를 사용합니다.
 - flow {...} 빌더의 내부 코드블록은 중단이 가능합니다.(=suspend)
 - flowSimple() 은 더 이상 suspend 키워드로 마킹되지 않습니다.
 - flow 에서는 emit 함수를 사용하여 결과값을 방출됩니다.
 - flow 에서는 collect 함수를 사용하여 결과값을 수집됩니다.

 Note) flowSimple() 코드 블록에서 delay() 함수를
 Thread.sleep() 으로 변경하면 메인스레드가 정지되는 것을 확인할 수 있습니다.
 ------------------------------------------------
    1
    2
    3
    I'm not blocked 1
    I'm not blocked 2
    I'm not blocked 3
 ------------------------------------------------


 ------------------------------------------------
 * Thread 와 Coroutine 에서 순차 처리를 비교하면서 설명하고 있습니다.
   단어의 사전적 의미를 함께 이해하면 도움이 될 것 같습니다.
   - yield 는 넘겨주다, 양도하다는 뜻을 가지고 있습니다.
   - emit 는 발산하다는 뜻을 가지고 있습니다.

 * flowSimple() 에 suspend 키워드를 추가해도 같은 결과를 확인할 수 있습니다.

 */