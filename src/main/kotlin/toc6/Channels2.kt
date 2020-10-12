package toc6

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel

fun main() = runBlocking {
    val channel = Channel<Int>()
    launch {
        for (x in 1..5) channel.send(x * x)
        channel.close() // we're done sending
    }
    // here we print received values using `for` loop (until the channel is closed)
    for(y in channel) { println(y) }
    println("Done!")
}

/**
 *  Closing and iteration over channels
 *

 Queue 와는 다르게, channel 은 종료를 통하여 더 이상 요소가 오지 않는다는 나타낼 수 있습니다.
 수신자 입장에서는 channel 통해 받는 요소를 정식 표현, for 문법으로 사용이 가능합니다.

 close() 는 특별한 종료 토큰을 channel 에 보내는 개념과 유사합니다.
 반복(=iteration)은 close 토큰 수신 즉시 멈추게 되며, 따라서
 close 가 수신 되기 전까지 발송(send) 되었던 요소들은 수신했다는 것이 보장됩니다.

 이 예제 코드의 출력 결과는 다음과 같습니다.

 ----------------------------------------
    1
    4
    9
    16
    25
    Done!
 ----------------------------------------

 Note1) for 구문에서 close 를 만나지 않으면 어떻게 될까요?
  > channel 에서 발송되는 값을 수신 대기하게 됩니다.
 Note2) 이미 close 된 채널에 다시 send 하게 될 경우 [ClosedSendChannelException] 이 발생되며,
   close 이 후 수신하려고 하면 [ClosedReceiveChannelException] 이 발생됩니다.
   자세한 내용은 close() 함수를 참조하세요.
*/