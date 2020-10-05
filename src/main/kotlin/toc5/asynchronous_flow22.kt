package toc5

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import kotlin.system.measureTimeMillis

fun main() = runBlocking {
    val nums = (1..3).asFlow().onEach { delay(300) } // numbers 1..3 every 300 ms
    val strs = flowOf("one", "two", "three").onEach { delay(400) } // strings every 400 ms
    val startTime = System.currentTimeMillis() // remember the start time
    nums.combine(strs) { a, b -> "$a -> $b" } // compose a single string with "combine"
            .collect { value -> // collect and print
                println("$value at ${System.currentTimeMillis() - startTime} ms from start")
            }
}

/**
 * Composing multiple flows
 * - Combine (2)
 *

 하지만 여기에 zip 대신 combine 연산자로 변경할 경우 아래처럼 출력됩니다:

 ------------------------------------------
    1 -> one at 437 ms from start
    2 -> one at 638 ms from start
    2 -> two at 840 ms from start
    3 -> two at 939 ms from start
    3 -> three at 1244 ms from start
 ------------------------------------------

 출력된 결과가 꽤 차이가 있는 것을 확인할 수 있는데, nums 와 strs 플로우에서
 각각 방출(emission) 될 경우 출력됩니다.

 */