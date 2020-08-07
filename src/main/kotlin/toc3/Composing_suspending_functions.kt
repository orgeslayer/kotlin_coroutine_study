package toc3

import kotlinx.coroutines.*
import kotlin.system.measureTimeMillis

suspend fun doSomethingUsefulOne(): Int {
    delay(1000L) // pretend we are doing something useful here
    return 13
}

suspend fun doSomethingUsefulTwo(): Int {
    delay(1000L) // pretend we are doing something useful here, too
    return 29
}

fun main() = runBlocking {
    val time = measureTimeMillis {
        val one = doSomethingUsefulOne()
        val two = doSomethingUsefulTwo()
        println("The answer is ${one + two}")
    }
    println("Completed in $time ms")
}

/**
 * Composing Suspending Functions
 *  - 이 장에서는 suspending functions 에 대한 다양한 접근 방법을 다룹니다.
 *
 * Sequencial by default
 *

 원격 서비스 호출이나 복잡한 계산을 담당해주는 유용한 중단 함수(suspending function)가 있다고 가정 해 봅시다.
 예제코드에서 두 가지 함수는 결과를 달성하기 위하여 지연을 둬서 유용한 함수인 것 처럼 작성 하였습니다.

 만약 첫 번째 doSomethingUsefulOne의 결과와, 두 번째 doSomethingUsefulTwo의 결과의 합을
 계산하려면 어떻게 해야할 까요? 실제로 우리는 첫 번째 함수의 결과를 이용해 두 번째 함수를 호출하거나 그 호출 방식을
 결정해야 할 경우 이런 상황이 발생합니다.

 코루틴의 경우, 일반적으로 작성된 코드들 처럼, 기본적으로 순차적으로 수행됩니다.
 예제 코드는 두 가지 일시 중단 기능을 실행하는 데 걸리는 총 시간을 측정하여 보여줍니다.
 (= 순차적으로 실행되기 때문에, 중단 함수 각각의 수행 시간의 합 만큼의 시간이 소모되었음을 확인할 수 있습니다.)
 */