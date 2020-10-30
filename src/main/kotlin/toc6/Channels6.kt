package toc6

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.*

fun CoroutineScope.produceDelayNumbers() = produce {
    var x = 1 // start from 1
    while (true) {
        send(x++) // produce next
        delay(100) // wait 0.1s
    }
}

fun CoroutineScope.launchProcessor(id: Int, channel: ReceiveChannel<Int>) = launch {
    for (msg in channel) {
        println("Processor #$id received $msg")
    }
}

fun CoroutineScope.launchConsumeProcessor(id: Int, channel: ReceiveChannel<Int>) = launch {
    channel.consumeEach { msg ->
        println("Processor #$id received $msg")
    }
}

fun main() = runBlocking {
    val producer = produceDelayNumbers()
    repeat(5) { launchProcessor(it, producer) }
    delay(950)
    producer.cancel() // cancel producer coroutine and thus kill them all
}

//fun main() = runBlocking {
//    val producer = produceDelayNumbers()
//    repeat(5) {
//        val job = launchProcessor(it, producer)
//        if (it == 3) {
//            delay( 200)
//            job.cancel()
//        }
//    }
//    delay(950)
//    producer.cancel() // cancel producer coroutine and thus kill them all
//}

/**
 * Fan-out
 *

 여러 코루틴이 동일한 채널에서 수신되어 서로간 작업을 분배할 수 있습니다.
 주기적으로 정수를 생산하는 생산자 코루틴을 만들어 봅시다. (초당 10개의 숫자)
 (=produceDelayNumbers 확장함수)

 그리고 여러 개의 프로세서 코루틴을 만듭니다. 이 예에서는 각각의 id 와 수신받은 숫자를 출력합니다.
 (=launchProcessor 확장함수, launch 부분 확인)

 이제 다섯개의 프로세서를 실행시키고 거의 1초간 동작되도록 합니다.
 그리고 어떤 일이 발생되는지 확인해 봅시다. 아마 출력된 결과는 각 정수를 수신하는 프로세서 ID에
 차이가 있을 수 있으나 거의 대부분 아래와 유사할 것입니다.

 ---------------------------------------------
    Processor #2 received 1
    Processor #4 received 2
    Processor #0 received 3
    Processor #1 received 4
    Processor #3 received 5
    Processor #2 received 6
    Processor #4 received 7
    Processor #0 received 8
    Processor #1 received 9
    Processor #3 received 10
 ---------------------------------------------

 생산자 코루틴을을 취소하면 채널이 닫히므로, 결국 프로세서 코루틴들 또한 닫히는 점에 유의하세요.

 또한 launchProcessor 코드에서 fan-out 을 수행하기 위해 채널에 for 구문을 사용한 점에
 주목하세요. comsumeEach 확장함수와 차이점은, for 반복문은 채널을 사용하는 다른 코루틴으로부터
 완벽하게 안전합니다. 만약 하나의 코루틴 프로세서가 실패하면 다른 코루틴이 여전히 채널에 대하여 프로세스를
 처리하는 반면, consumeEach 확장함수를 이용하는 프로세서 코루틴의 경우 정상 혹은 비정상 종료에 대하여
 consumes(취소)를 전파받아 모두 종료될 것입니다.

 ---------------------------------------------
 * for 구문과 consumeEach 에 대한 차이는 예제 코드의 main() 을 하단의 main() 코드로 변경하고,
  launchProcessor / launchConsumeProcessor 를 변경하여 실행해보면 차이를 확인할 수 있습니다.
 */