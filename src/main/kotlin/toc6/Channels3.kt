package toc6

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.*

fun CoroutineScope.produceSquares(): ReceiveChannel<Int> = produce {
    for (x in 1..5) send(x * x)
}

fun main() = runBlocking {
    val squares = produceSquares()
    squares.consumeEach { println(it) }
    println("Done!")
}

/**
 *  Building channel producers
 *

 코루틴에서 순차적으로 요소를 생성해 내는 패턴은 꽤 흔한 일입니다.
 이는 동시성처리 코드에서 흔히 확인되는 producer - consumer 패턴의 한 부분입니다.
 producer 생성 작업을 추상화 하기 위해서 채널을 파라미터로 전달받는 생성함수로 만들 수 있습니다.
 하지만 이는 함수는 반드시 결과를 리턴한다는 상식과 어긋납니다.

 producer 관점으로 편리하게 코루틴을 생성하는 produce 코루틴 빌더가 있으며,
 consumer 관점에서 반복 작업하는 처리를 위한 consumeEach 확장 함수가 있습니다.
*/