package toc5

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

suspend fun performRequest(request: Int): String {
    delay(1000) // imitate long-running asynchronous work
    return "response $request"
}

fun main() = runBlocking {
    (1..3).asFlow() // a flow of requests
        .map { request -> performRequest(request) }
        .collect { response -> println(response) }
}

/**
 * Intermediate flow operators
 *

 flow 는 컬렉션이나 시퀀스에서 해오던것 처럼 연산자를 통해 변환될 수 있습니다.
 중간 연산자들은 업스트림 플로우에 적용되어 다운스트림을 반환합니다. 이러한 오퍼레이션들은
 플로우가 그렇듯 콜드 타입으로 동작되며, 각 연산자 호출 자체는 중단함수가 아닙니다.
 새롭게 변환된 flow 의 결과를 되돌려 주는것은 빠르게 동작됩니다.

 기본적인 연산자로 map 이나 filter 와 같은 친숙한 이름을 가지고 있습니다.
 시퀀스를 처리하는데 있어 중요한 것은, 연산 수행하는 내부 코드블록은 중단함수를 호출할 수 있다는 것입니다.

 예를들어 요청 flow 는 map 연산자를 통해 결과값으로 매핑될 수 있으며,
 오랜 시간 처리되는 각 요청들을 중단함수로 구현함으로써 동작됩니다.

 예제코드는 3개의 라인을 생성하며, 각 라인은 매 초 후에 나타납니다.

 ------------------------------------------------
    response 1
    response 2
    response 3
 ------------------------------------------------

 */