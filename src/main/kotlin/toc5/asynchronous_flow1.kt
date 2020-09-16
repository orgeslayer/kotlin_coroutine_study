package toc5

fun simple(): List<Int> = listOf(1, 2, 3)

fun main() {
    simple().forEach { value -> println(value) }
}

/**
 * Asynchronous Flow
 *  - 하나의 중단 함수는 비동기적으로 하나의 값만 리턴하는데요,
 *    어떻게 하면 비동기적으로 여러개의 연산수행된 값을 리턴되도록 할 수 있을까요?
 *  - 코틀린 플로우에 대하여 확인할 차례입니다.
 *
 * Representing multiple values
 *

 여러개의 값은 코틀린의 collections 이용하여 표현할 수 있습니다.
 예를들어 simple 함수를 통해 세 개의 숫자 목록(List) 를 반환하는 함수를 만들고,
 forEach 를 이용하여 전체를 출력할 수 있습니다.

 예제 코드는 다음과 같은 실행 결과를 확인할 수 있습니다.

 --------------------------
    1
    2
    3
 --------------------------

 */