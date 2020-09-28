package toc5

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import kotlin.system.measureTimeMillis

fun main() = runBlocking {
    val nums = (1..3).asFlow().onEach { delay(300) } // numbers 1..3 every 300 ms
    val strs = flowOf("one", "two", "three").onEach { delay(400) } // strings every 400 ms
    val startTime = System.currentTimeMillis() // remember the start time
    nums.zip(strs) { a, b -> "$a -> $b" } // compose a single string with "zip"
        .collect { value -> // collect and print
            println("$value at ${System.currentTimeMillis() - startTime} ms from start")
        }
}

/**
 * Composing multiple flows
 * - Combine (1)
 *

 flow 가 어떤 연산이나 상태의 최근 값을 나타낼 때(Conflation 관련 내용을 참조하세요.),
 그 플로우의 최근 값에 추가 연산을 수행하거나 또는 별도의 업스트림 플로우가 값을 방출할 때마다
 이를 다시 계산해야 할 수 있습니다. 이와 관련된 연산자를 combine 이라고 부릅니다.

 예를들어, 이전의 예제에서 숫자는 300ms 마다 업데이트 되고, 문자열은 400ms 마다 업데이트 될 때,
 zipp 연산자를 활용할 경우 앞선 예제의 출력결과와 동일함을 확인할 수 있습니다.
 (다만 매 출력은 약 400ms 정도 소요됩니다.)

 Note) 이 예에서 사용한 onEach 중간 연산자는 각 원소의 방출 주기를 지연시키면서 코드를 보다
 선언적이며 짧게 만들어 줍니다.

 */