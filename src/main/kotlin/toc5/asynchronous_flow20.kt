package toc5

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import kotlin.system.measureTimeMillis

fun simpleFlowForZip(): Flow<Int> = flow {
    for(i in 1..3) {
        delay(500)
        emit(i)
    }

}

fun main() = runBlocking {
//    val nums = simpleFlowForZip()
    val nums = (1..3).asFlow() // numbers 1..3
    val strs = flowOf("one", "two", "three") // strings
    nums.zip(strs) { a, b -> "$a -> $b" } // compose a single string
        .collect { println(it) } // collect and print
}

/**
 * Composing multiple flows
 * - 여러 흐름들을 구성하는 방법은 매우 많습니다.
 * - Zip
 *

 코틀린 표준 라이브러리의 시퀀스 zip 확장함수 처럼 flow 에도 두 flow 의 해당 값을 결합하는
 zip 연산자가 있습니다. 예제코드는 다음과 같이 출력됩니다.

 ----------------------------------------
    1 -> one
    2 -> two
    3 -> three
 ----------------------------------------

 * 두 개의 flow 의 원소 개수가 다르다면, 적은 수를 기준으로 zip 결과가 수집됩니다. 예를들어,
   num 이 1..5 로 되어있다고 하더라도 동일한 출력결과를 확인할 수 있습니다.
 * 두 개의 flow 에서 각 값을 방출하는 타이밍이 다를 경우, 나중에 방출되는 값과 함께 zip 결과를 확인할 수 있습니다.
   예제코드의 nums 를 simpleFlowForZip() 으로 변경 후 확인이 가능합니다.
 */