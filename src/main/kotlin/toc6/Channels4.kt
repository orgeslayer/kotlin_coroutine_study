package toc6

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.*


fun CoroutineScope.produceNumbers() = produce {
    var x = 1
    while (true) send(x++) // infinite stream of integers starting from 1
}

fun CoroutineScope.square(numbers: ReceiveChannel<Int>): ReceiveChannel<Int> = produce {
    for (x in numbers) send(x * x)
}

fun main() = runBlocking {
    val numbers = produceNumbers() // produces integers from 1 and on
    val squares = square(numbers) // squares integers
    repeat(5) {
        println(squares.receive()) // print first five
    }
    println("Done!") // we are done
    coroutineContext.cancelChildren() // cancel children coroutines
}

/**
 * Pipelines
 *

 Pipeline 은 먼저 값을 가능한한 무한대로 생산해내는 코루틴이 있고 (=produceNumbers 확장함수),
 하나 또는 여러 코루틴이 생산되는 스트림을 필요한 만큼 소비하면서 처리하여 (=square 확장함수),
 다른 결과를 생산하는 패턴입니다.

 이 예제는 메인 코드에서 전체 파이프라인을 시작하고 연결하며,
 단순 숫자를 제곱하는 결과를 확인할 수 있습니다.

 Note) 우리는 코루틴에서 생성된 모든 함수는 CoroutineScope의 확장 함수로 정의 할 수 있으며,
 이는 우리 어플리케이션에 글로벌 코루틴이 남지 않도록 구조화된 동시성을 보장할 수 있습니다.

 */