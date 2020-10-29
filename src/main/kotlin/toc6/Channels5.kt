package toc6

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.*

fun CoroutineScope.numbersFrom(start: Int) = produce {
    var x = start
    while (true) {
//        println("[Pipeline] $x")
        send(x++)
    } // infinite stream of integers from start
}

fun CoroutineScope.filter(numbers: ReceiveChannel<Int>, prime: Int) = produce {
    for (x in numbers) {
//        println("[Prime Filter] Pipeline : $x, prime : $prime")
        if (x % prime != 0) {
//            println("[filter] prime : $prime, send : $x")
            send(x)
        }
    }
}

fun main() = runBlocking {
    var cur = numbersFrom(2)
    repeat(10) {
        val prime = cur.receive()
        println(prime)
        cur = filter(cur, prime)
    }
    coroutineContext.cancelChildren() // cancel all children to let main finish
}

/**
 * Prime numbers with pipeline
 *

 코루틴의 파이프라인을 이용하여 소수(Prime numbers) 를 출력하는
 조금 극단적인 예를 들어보겠습니다. (=왜 극단적인지는 나중에 다시 설명합니다.)
 무한한 숫자를 순서대로 출발합니다. (=numbersFrom 확장함수)
 다음 파이프라인은 들어오는 숫자를 필터링 하여 전달된 소수로부터 구분할 수 있는
 모든 숫자를 제거합니다. (=filter 확장함수)

 이제 우리는 2부터 숫자값을 스트리밍하는 파이프라인을 시작시키고,
 현재 채널로부터 소수를 취하고, 새로운 소수가 발견될 때마다 새로운 파이프라인을 개시합니다.(staging)

 ---------------------------------------------
    numbersFrom(2) -> filter(2) -> filter(3) -> filter(5) -> filter(7) ...
 ---------------------------------------------

 이 예제에서는 첫 10개의 소수점을 출력하며, 전체 파이프라인을 메인스레드의 컨텍스트에서 실행합니다.
 실행된 모든 코루틴들은 메인 코루틴 (runBlocking) 스코프에서 수행되므로, 우리가 실행시킨
 모든 코루틴들을 명시적으로 유지할 필요가 없습니다. 첫 10개의 소수를 출력하고 난 후,
 cancelChildren 확장 함수를 이용하여 모든 자식 코루틴들을 취소합니다.
 이 예제코드는 다음과 같이 출력됩니다:

 ---------------------------------------------
    2
    3
    5
    7
    11
    13
    17
    19
    23
    29
 ---------------------------------------------

 표준 라이브러리에서 제공하는 iterator 코루틴 빌더를 통해 동일한 파이프라인을 만들 수 있습니다.
 produce > iterator,
 send > yield,
 receive > next,
 ReceivedChannel > Iterator,
 로 변경하고 코루틴 스코프를 제거하면 됩니다. (=하단 주석 코드를 참조하세요.)
 이렇게 처리하면 더 이상 runBlocking 필요하지 않습니다. 하지만 채널을 사용하는 파이프라인 구현방식은
 멀티코어 환경에서 Dispatchers.Default 컨텍스트를 사용하여 동시에 실행시키는 이점이 있습니다.

 어쨌든, 이 예제는 소수를 찾는데 극도로 비실용적인 방법입니다. 실제는, 파이프라인은
 몇몇 다른 중단함수를 호출하는 것을 포함하는데 (원격 서비스에 대한 비동기 호출처럼)
 이러한 파이프라인은 sequence/iterator 같은 것으로 만들 수 없습니다. 왜냐하면,
 이것들은 produce 와는 다르게 완전히 비동기적으로 다른 중단함수 실행을 허용하지 않기 때문입니다.
 */

//fun numbersFrom(start: Int) = iterator {
//    var x = start
//    while (true) yield(x++)
//}
//
//fun filter(numbers: Iterator<Int>, prime: Int) = iterator {
//    for (x in numbers) if (x % prime != 0) yield(x)
//}
//
//fun main() {
//    var cur = numbersFrom(2)
//    repeat(10) {
//        val prime = cur.next()
//        println(prime)
//        cur = filter(cur, prime)
//    }
//}