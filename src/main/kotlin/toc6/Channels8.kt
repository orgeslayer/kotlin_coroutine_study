package toc6

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.*

fun main() = runBlocking {
    val channel = Channel<Int>(4) // create buffered channel
    val sender = launch { // launch sender coroutine
        repeat(10) {
            println("Sending $it") // print before sending each element
            channel.send(it) // will suspend when buffer is full
//            println("Sending $it Done")
        }
    }
    // don't receive anything... just wait....
    delay(1000)
    sender.cancel() // cancel sender coroutine
}

/**
 * Buffered channels
 *

 지금까지 확인한 채널들은 버퍼가 없었습니다.
 버퍼가 없는 채널은 송신자(sender)와 수신자(receiver)가 만날 때 요소들을 전송하게 됩니다. (마치 랑데뷰처럼)
 만약 송신(send)이 먼저 발생되면, 수신(receive) 가 발생되기 전까지 중단(suspended) 되며,
 반대로 수신(receive)이 먼저 발생되면, 송신(send) 이 될 때까지 중단(suspended) 됩니다.

 Channel<T> 팩토리 함수와 produce{} 빌더는 버퍼 사이즈를 명시하기 위하여
 옵션으로 capacity 를 파라미터로 지정 할 수 있습니다. 버퍼는 송신자가 여러개의 요소를
 중단되기 전까지 송신할 수 있도록 허용하며, 이는 버퍼가 가득 찼을 때 차단(block) 되는
 BlockingQueue 에 명시적으로 capacity 를 지정하는 것과 유사합니다.

 예제 코드의 동작을 살펴봅니다.
 4개의 버퍼가 있는 채널에 "Sending" 을 다섯번 출력하게 됩니다.

 ----------------------------------------
    Sending 0
    Sending 1
    Sending 2
    Sending 3
    Sending 4
 ----------------------------------------

 처음 4개의 요소는 버퍼에 추가가 되고 다섯번째 요소를 송신하려고 하면 중단 됩니다.

 */