package toc5

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

suspend fun performDelayedRequest(request: Int): String {
    delay(1000) // imitate long-running asynchronous work
    return "response $request"
}

fun main() = runBlocking {
    (1..3).asFlow() // a flow of requests
        .transform { request ->
            emit("Making request $request")
            emit(performDelayedRequest(request))
        }
        .collect { response -> println(response) }
}

/**
 * Transform operator
 *

 flow 변환 연산자 중 가장 일반적인 것 중 하나가 transform 입니다.
 map 이나 filter 같은 단순한 변환을 모방하여 사용하는 것 뿐만 아니라,
 좀 더 복잡한 변환에도 사용이 가능합니다. transform 연산자를 사용하여,
 임의의 값을 임의의 횟수로 emit 할 수 있습니다.

 예를들어, transform 을 사용하면 오랜시간 수행해야하는 비동기 요청을 수행하기 전에
 문자열을 emit 하고 요청에 대한 응답이 도착하면 그 결과를 emit 할 수 있습니다.

 예제코드는 다음과 같이 출력됩니다.
 ---------------------------------------------
    Making request 1
    response 1
    Making request 2
    response 2
    Making request 3
    response 3
 ---------------------------------------------

 */