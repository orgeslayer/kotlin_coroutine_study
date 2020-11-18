package toc5

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import java.lang.RuntimeException

fun flowExample() = flow {
    for(i in 1..10) {
        if(i == 3) throw Exception("$i")
        else emit(i)
    }
}

fun main() = runBlocking {
    val flow = flowExample()
    // common case - onEach > catch > complete
    flow.onEach { println("Collect $it") }
        .catch { cause -> emit(-1) }
        .onCompletion { println("Flow completed") }
        .collect()
    println("-".repeat(20))

    // handle exception routine - catch > onEach > complete
    flow.catch { cause -> emit(-1) }
        .onEach { println("Collect $it") }
        .onCompletion { println("Flow completed") }
        .collect()
    println("-".repeat(20))

    flow.onCompletion { println("Flow completed") }
        .catch { cause -> emit(-1) }
        .onEach { println("Collect $it") }
        .collect()
    println("-".repeat(20))

    flow.onCompletion { println("Flow completed") }
        .onEach { println("Collect $it") }
        .catch { cause -> emit(-1) }
        .collect()
}

/**
 * 추가) Declarative Exception
 *

 flow 수집 동작 중 예외가 발생하는 경우를 좀 더 살펴보겠습니다.
 일반적으로 flow 를 선언적으로 사용할 경우 각 수집된 값을 정의하고, 에러를 처리하며, 최종 결과를 처리하도록
 작성할 것입니다. (= 첫 번째 예제입니다.)

 만약, 에러가 발생되었을 경우, 추가적으로 의미있는 값을 송출하려면 어떻게 해야 할까요?
 catch 코드블록에서 emit 을 실행하면 됩니다. (= 두 번째 예제입니다.)
 단, 이 경우 catch 코드블록에서 emit 을 먼저 선언해줘야 collect 가 값을 정상적으로 수신할 수 있습니다.
 (첫 번째 예제와 두 번째 예제의 차이는 catch 코드블록과 onEach 코드블록 선언 순서에 유의하세요.)

 예외 처리를 할 때, onComplete 동작 역시 선언 순서에 영향을 받습니다.
 onComplete 은 방출가의 코드블록이 모두 종료될 경우 호출되므로, onComplete 코드블록을
 먼저 선언 후 catch 코드블록이 선언되어 있을 경우 (= 세 번째 예제입니다.) 예상치 못한 실행 동작을 확인할 수 있습니다.
 따라서, onComplete 와 catch 코드블록을 함께 선언할 경우, onComplete 코드블록은 수집가의 마지막에 선언 하는 것이 좋습니다.

 */