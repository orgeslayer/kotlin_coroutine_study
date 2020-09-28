package toc5

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import kotlin.system.measureTimeMillis

fun requestFlow(i: Int): Flow<String> = flow {
    emit("$i: First")
    delay(500) // wait 500 ms
    emit("$i: Second")
}

fun main() = runBlocking {
    (1..3).asFlow().map { requestFlow(it) }.collect { flow ->
        flow.collect { value ->
            println(value)
        }
    }
    Unit
}

/**
 * Flattening flows
 *

 flow 는 순차적인 결과를 비동기적으로 나타내기 때문에, 각각의 값을 다른 값들의 시퀀스로
 요청해야 하는 상황은 자주 발생됩니다. 예를들어, 500ms 간격으로 두개의 문자열을 리턴하는
 flow 를 정의할 수 있습니다.

 -----------------------------------
    fun requestFlow(i: Int): Flow<String> = flow {
        emit("$i: First")
        delay(500) // wait 500 ms
        emit("$i: Second")
    }
 -----------------------------------

 만약 세 개의 숫자를 방출하는 flow 가 있고, requestFlow() flow 각각을 호출한다면
 아래와 같을 것입니다:

 -----------------------------------
    (1..3).asFlow().map { requestFlow(it) }
 -----------------------------------

 이 경우 우리는 flow 들의 flow 를 얻게 되며,( Flow<Flow<String>> )
 더 나은 처리를 위한 single flow 로의 플래트 작업이 필요하게 됩니다.
 컬렉션이나 시퀀스는 이러한 처리를 위하여 flatten, flatMap 연산자가 있습니다.
 하지만, flow 에서 모드에 따른 비동기 처리 방식에 따라 플래트닝 처리 모드에 차이가 있어,
 flow 에도 유사하지만 플래트닝 연산자가 있습니다.

 * flattening 을 평탄화, 납짝하게하다 등의 번역이 있지만 여기서는 플래트닝 이라는 표현 그대로
 번역을 하였습니다.

 */