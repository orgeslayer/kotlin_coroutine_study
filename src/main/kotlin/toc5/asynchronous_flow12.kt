package toc5

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

fun main() = runBlocking {
    (1..5).asFlow()
        .filter {
            println("Filter $it")
            it % 2 == 0
        }
        .map {
            println("Map $it")
            "string $it"
        }.collect {
            println("Collect $it")
        }
}

/**
 * Flows are sequential
 *

 flow 에서 수집되는 개별적인 요소는 복수의 flow 를 제어하는 특별한 연산자가 없다면
 순차적으로 수행된다. collect 은 종료 연산자가 호출될 때 수행되며 코루틴에서 바로 처리됩니다.
 기본적으로 새로운 코루틴을 생성하지 않습니다.



 */