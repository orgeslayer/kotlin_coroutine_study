package toc6

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.*

data class Ball(var hits: Int)

fun main() = runBlocking {
    val table = Channel<Ball>() // a shared table
    launch { player("ping", table) }
    launch { player("pong", table) }
    table.send(Ball(0)) // serve the ball
    delay(1000) // delay 1 second
    coroutineContext.cancelChildren() // game over, cancel them
}

suspend fun player(name: String, table: Channel<Ball>) {
    for (ball in table) { // receive the ball in a loop
        ball.hits++
        println("$name $ball")
        delay(300) // wait a bit
        table.send(ball) // send the ball back
    }
}

/**
 * Channels are fair
 *

 채널에 여러개의 코루틴에서 송신 및 수신을 수행한다면 그 실행순서는 호출 순서에 따라 공정하게(fair) 실행합니다.
 FIFO (First-in First-out) 방식으로 스케쥴링되며, 이것은 먼저 수신(receive)를 호출한 코루틴이
 요소를 전달받는다고 할 수 있습니다. 이 예제에서는 "ping" 과 "pong" 두 개의 코루틴이 "ball" 객체를
 "table" 이라는 하나의 채널에서 수신하는 예제입니다.

 "ping" 코루틴이 먼저 시작되었기 때문에, 먼저 ball 을 수신하게 됩니다.
 "ping" 코루틴에서 table 채널로 송신 후 즉시 수신하려 하지만,
 "pong" 코루틴에서 이미 수신을 기다리고 있었기 때문에 "pong" 코루틴이 수신을 하게 됩니다.

 ----------------------------------------
    ping Ball(hits=1)
    pong Ball(hits=2)
    ping Ball(hits=3)
    pong Ball(hits=4)
 ----------------------------------------

 참조) 때때로 채널이 공정하지 않게(unfair) 실행되는 모습을 보이기도 하는데,
 이는 채널을 사용하는 Executor의 특성 때문입니다.
 자세한 내용은 해당 이슈를 참조하세요.
 (https://github.com/Kotlin/kotlinx.coroutines/issues/111)

 */