package toc5

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

fun launchEvents(): Flow<Int> = (1..3).asFlow().onEach { delay(100) }

fun main() = runBlocking {
    launchEvents()
        .onEach { event -> println("Event: $event") }
        .launchIn(this) // <--- Launching the flow in a separate coroutine
    println("Done")
}


/**
 * Launching flow (2)
 *

 launchIn 종료 연산자가 여기에서 유용하게 사용될 수 있습니다.
 (이전 예제에서) collect 를 launchIn 으로 변경함으로써 flow 의 수집 동작을 분리된 코루틴으로 실행이 가능하며,
 따라서 이 후에 작성된 코드가 즉시 즉시 실행됩니다. 아래와 같이 출력됩니다 :

 ---------------------------------------------
    Done
    Event: 1
    Event: 2
    Event: 3
 ---------------------------------------------

 launchIn 연산자는 flow 가 실행되어 수집을 수행하기 위하여 명시적인 CoroutineScope 지정이 필요합니다.
 위 예에서는 runBlocking 코루틴 빌더로부터 획득한 스코프를 사용함으로써, runBlocking 스코프는
 하위 코루틴의 완료를 기다리기 때문에 메인 함수(=runBlocking) 가 반환되어 프로그램이 종료되지 않도록 유지합니다.

 실제 어플리케이션에서 스코프는 제한된 생명주기를 갖는 요소(=entity)에서 전달될 수 있습니다.
 이러한 요소(=entity)의 생명주기가 종료 되면, 그 즉시 엔티티로부터 전달된 스코프는 취소가 되어지며,
 flow 의 수집 또한 취소됩니다. 이런식으로, onEach {...}.launchIn(scope) 와 addEventListener 는
 동일하게 수행됩니다.(=개념적으로 그렇다는 의미) 반면, 취소(=cancellation) 와 구조화된 동시성처리를 제공하기 때문에
 removeEventListener 는 굳이 필요하지 않습니다.

 Note) launchIn 또한 job 을 반환하는 것을 주의하세요. 이는 전체 스코프를 취소하거나 특정 Job 에 대한 join 하지 호출하지 않고,
 flow 의 수집동작을 수행하는 코루틴만 취소하는데 사용할 수 있습니다.

 */