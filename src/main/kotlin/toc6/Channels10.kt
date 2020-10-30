package toc6

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.*

fun main() = runBlocking {
    val tickerChannel = ticker(delayMillis = 100, initialDelayMillis = 0) // create ticker channel
    var nextElement = withTimeoutOrNull(1) { tickerChannel.receive() }
    println("Initial element is available immediately: $nextElement") // no initial delay

    nextElement = withTimeoutOrNull(50) { tickerChannel.receive() } // all subsequent elements have 100ms delay
    println("Next element is not ready in 50 ms: $nextElement")

    nextElement = withTimeoutOrNull(60) { tickerChannel.receive() }
    println("Next element is ready in 100 ms: $nextElement")

    // Emulate large consumption delays
    println("Consumer pauses for 150ms")
    delay(250)
    // Next element is available immediately
    nextElement = withTimeoutOrNull(1) { tickerChannel.receive() }
    println("Next element is available immediately after large consumer delay: $nextElement")
    // Note that the pause between `receive` calls is taken into account and next element arrives faster
    nextElement = withTimeoutOrNull(60) { tickerChannel.receive() }
    println("Next element is ready in 50ms after consumer pause in 150ms: $nextElement")

    tickerChannel.cancel() // indicate that no more elements are needed
}

/**
 * Ticker channels
 *

 Ticker 채널은 마지막으로 수신된 내용이 소비되고 난 후, 주어진 시간마다 반복적으로
 지연된 Unit 을 생성하는 특별한 랑데뷰(rendezvous) 채널입니다. 혼자서는 쓸모없어
 보이지만, 시간을 기반으로 복잡하게 운용되는 produce 파이프라인이나 다른 시간과 의존적인
 연산들과는 유용할 수 있습니다. Ticker 채널은 select 를 이용하여 "on tick" 액션을
 수행합니다.

 Ticker 채널은 ticker 팩토리 함수로 생성이 가능합니다. 그리고
 더 이상 요소가 필요없다면 ReceivedChanel.cancel 함수를 사용하세요.

 이제 예제를 보면서 어떻게 동작하는지 확인 해 봅시다. 이 예제코드는 아래와 같이 출력됩니다.

 ----------------------------------------
    Initial element is available immediately: kotlin.Unit
    Next element is not ready in 50 ms: null
    Next element is ready in 100 ms: kotlin.Unit
    Consumer pauses for 150ms
    Next element is available immediately after large consumer delay: kotlin.Unit
    Next element is ready in 50ms after consumer pause in 150ms: kotlin.Unit
 ----------------------------------------

 ticker 는 기본적으로 정지 발생 시 소비자가 중단되었을 가능성을 인지하고 있으며,
 생산되는 요소의 고정 비율(속도)를 유지하려고 시도합니다.

 선택적으로 ticker 는 요소들 사이에 지연간격을 고정적으로 적용할 수 있도록
 mode 파라미터에 TickerMode.FIXED_DELAY 를 사용할 수 있습니다.
 */