package toc6

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel

fun main() = runBlocking {
    val channel = Channel<Int>()
    launch {
        // this might be heavy CPU-consuming computation or async logic, we'll just send five squares
        for (x in 1..5) channel.send(x * x)
    }
    // here we print five received integers:
    repeat(5) { println(channel.receive()) }
    println("Done!")
}



/**
 *  Channels
 *  - 지연된 값 (Deferred Value)은 코루틴 간 편리하게 하나의 값을 공유할 수 있습니다.
 *    Channels 은 값의 스트림을 전달할 수 있는 방법을 제공합니다.
 *  - Chanel basic
 *

 Channel 은 BlockingQueue 와 개념적으로 매우 유사합니다.
 한가지 중요한 차이점은, Blocking 에서 사용하는 put() 함수 대신 send() 라는 suspending 함수가 있으며,
 take() 함수 대신 receive() 라는 suspending 함수가 있다는 점입니다.

 이 예제 코드의 출력 결과는 다음과 같습니다.

 ----------------------------------------
    1
    4
    9
    16
    25
    Done!
 ----------------------------------------

 * BlockingQueue 에 대한 내용은 아래 링크를 참조하세요.
    - http://tutorials.jenkov.com/java-concurrency/blocking-queues.html
*/