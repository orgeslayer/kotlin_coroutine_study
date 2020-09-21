package toc5

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

fun flowColdSimple(): Flow<Int> = flow {
    println("Flow started")
    for(i in 1..3) {
        delay(100) // pretend we are doing something useful here
        emit(i) // emit next value
    }
}

fun main() = runBlocking {
    println("Calling simple function...")
    val flowColdSimple = flowColdSimple()
    println("Calling collect...")
    flowColdSimple.collect { value -> println(value) }
    println("Calling collect again...")
    flowColdSimple.collect { value -> println(value) }
}

/**
 * Representing multiple values
 * - Flows are cold
 *

 Flow는 시퀀스처럼 코드 스트림입니다. - flow{} 빌더 내부의 코드블록은
 flow가 수집될 때 까지 실행되지 않습니다. 이것은 다음 예제를 확인해보면 명확해 집니다.

 ----------------------------------------
    Calling simple function...
    Calling collect...
    Flow started
    1
    2
    3
    Calling collect again...
    Flow started
    1
    2
    3
 ----------------------------------------

 주요 이유는 (flow 를 리턴하는)예제 함수는 suspend 식별자가 마킹되어있지 않기 때문입니다.
 flowColdSimple() 함수 호출 즉시 결과를 반환하며, 아무것도 대기하지 않습니다.
 flow는 collect 함수 호출 때마다 매번 시작되며, 그것이 예제에서 "Flow started" 출력을
 collect 함수 호출 시마다 확인하게 되는 이유입니다.

 ---------------------------------------------------------------
 * Data streaming 특징을 표현할 때 hot / cold 라는 키워드를 활용하는 것 같아 비교 설명을 잘 정리한 내용이 있어 추가 합니다.

    From: Anton Moiseev Book "Angular Development with Typescript, Second Edition." :
    Hot and cold observables

    There are two types of observables: hot and cold.
    The main difference is that a cold observable creates a data producer for each subscriber,
    whereas a hot observable creates a data producer first,
    and each subscriber gets the data from one producer, starting from the moment of subscription.

    Let's compare watching a movie on Netflix to going into a movie theater.
    Think of yourself as an observer. Anyone who decides to watch Mission:
    Impossible on Netflix will get the entire movie, regardless of when they hit the play button.
    Netflix creates a new producer to stream a movie just for you. This is a cold observable.

    If you go to a movie theater and the showtime is 4 p.m., the producer is created at 4 p.m., and the streaming begins.
    If some people (subscribers) are late to the show, they miss the beginning of the movie
    and can only watch it starting from the moment of arrival. This is a hot observable.

    A cold observable starts producing data when some code invokes a subscribe() function on it.
    For example, your app may declare an observable providing a URL on the server to get certain products.
    The request will be made only when you subscribe to it.

    If another script makes the same request to the server, it'll get the same set of data.
    A hot observable produces data even if no subscribers are interested in the data.
    For example, an accelerometer in your smartphone produces data about the position of your device,
    even if no app subscribes to this data. A server can produce the latest stock prices even if no user is interested in this stock.


 */