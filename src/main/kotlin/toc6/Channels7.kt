package toc6

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.*

suspend fun sendString(channel: SendChannel<String>, s: String, time: Long) {
    while (true) {
        delay(time)
        channel.send(s)
    }
}

fun main() = runBlocking {
    val channel = Channel<String>()
    launch { sendString(channel, "foo", 200L) }
    launch { sendString(channel, "BAR!", 500L) }
    repeat(6) { // receive first six
        println(channel.receive())
    }
    coroutineContext.cancelChildren() // cancel all children to let main finish
}

/**
 * Fan-in
 *

 여러개의 코루틴들이 동일 대상 채널에 전송할 수도 있습니다. 예를들어, 문자열 형식의 채널이 있고,
 이 채널로 지정된 지연시간동안 반복적으로 문자열을 전송하는 중단함수가 있다고 생각해 봅시다.
 (=sendString 함수)

 이제 두 개의 코루틴에서 문자열을 전송할 때 어떤 일이 발생하는지 확인해 봅시다.
 (이 예제에서는 이 코루틴들을 메인 스레드의 메인 코루틴의 자식으로 실행시킵니다.)

 출력 결과는 다음과 같습니다.

 ----------------------------------------
    foo
    foo
    BAR!
    foo
    foo
    BAR!
 ----------------------------------------

 */