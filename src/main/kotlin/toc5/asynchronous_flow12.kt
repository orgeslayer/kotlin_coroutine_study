package toc5

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

fun main() = runBlocking {
    (1..5).asFlow()
        .filter {
//            delay(1000)
            println("Filter $it")
            it % 2 == 0
        }
        .map {
//            delay(1000)
            println("Map $it")
            "string $it"
        }
        .collect {
//            delay(1000)
            println("Collect $it")
        }
}

/**
 * Flows are sequential
 *

 flow 에서 수집되는 개별적인 요소(=emit value)는 복수의 flow 를 제어하는 특별한 연산자가 없다면
 순차적으로 수행됩니다. 컬렉션은 종료 연산자가 호출될 때 해당 코루틴에서 직접 실행됩니다.
 기본적으로 새로운 코루틴을 생성하지 않습니다. 각각의 emit 값은 중간 연산자에 의해 처리하면서
 상류(=upstream)에서 하류로(=downstream) 전달되며, 그 후에 종료 연산자에게 전달됩니다.

 짝수를 찾아서 문자열로 변환하는 예를 봅시다. 출력결과 :

 --------------------------------------------------
    Filter 1
    Filter 2
    Map 2
    Collect string 2
    Filter 3
    Filter 4
    Map 4
    Collect string 4
    Filter 5
 --------------------------------------------------

 */