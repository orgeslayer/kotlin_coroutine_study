package toc5

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

fun main() = runBlocking {
    val sum = (1..5).asFlow()
        .map {
//            println("map : $it")
            it * it
        } // squares of numbers from 1 to 5
        .reduce { a, b ->
//            println("reduce : $a, $b")
            a + b
        } // sum them (terminal operator)
    println(sum)
}

/**
 * Terminal flow operators
 *

 flow 에서 종료 연산자는 flow collect 을 시작하는 중단함수입니다.
 collect 는 가장 기본적인 연산자이지만, 다음과 같은 좀더 편리한 종료 연산자들이 있습니다.

 - toList 나 toSet 같은 다양한 컬렉션으로 변환
 - 첫 번째 값을 받기 위한 first 연산자나, 하나의 값을 보장해주는 single 연산자
 - reduce 나 fold 같이 flow 의 값을 감소시키기 위한 연산자

 예제 코드는 하나의 숫자를 출력합니다: 55

 */