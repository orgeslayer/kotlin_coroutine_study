package toc5

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*


// Imitate a flow of events
fun events(): Flow<Int> = (1..3).asFlow().onEach { delay(100) }

fun main() = runBlocking {
    events()
        .onEach { event -> println("Event: $event") }
        .collect() // <--- Collecting the flow waits
    println("Done")
}


/**
 * Launching flow (1)
 *

 어떤 소스에서 비동기 이벤트들을 획득하고 나타내기 위해서 flow 를 사용하는 것은 쉽게 표현할 수 있습니다.
 이 경우, 각 이벤트를 획득하고 이에 대한 추가 작업을 계속하는 'addEventListener' 같은 아날로그 방식의
 함수를 등록학여 처리가 가능합니다. flow 에서 onEach 연산자는 이러한 역할을 수행합니다.
 하지만 onEach 연산자는 중간 연산자입니다. 우리는 flow 수집을 시작하기 위한 목적으로 종료 연산자가 필요합니다.
 그렇지 않으면 onEach 만 호출하는 것은 아무런 효과가 없습니다.

 만약 collect 종료 연산자를 onEach 이 후에 호출하면, 이 예제코드는 flow 가 수집 완료될 때 까지 기다립니다.
 출력 결과는 다음과 같습니다.

 ----------------------------------------
    Event: 1
    Event: 2
    Event: 3
    Done
 ----------------------------------------

 */