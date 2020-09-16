package toc5

fun sequence(): Sequence<Int> = sequence { // sequence builder
    for (i in 1..3) {
        Thread.sleep(100) // pretend we are computing it
        yield(i) // yield next value
    }
}

fun main() {
    sequence().forEach { value -> println(value) }
}

/**
 * Representing multiple values
 * - Sequences
 *

 각 수에 대하여 CPU 가 많이 소모 후 계산되는 경우, Sequence를 사용하여 숫자를 나타낼 수 있습니다.
 (예제에서는 각 연산에서 100ms 의 시간이 소요된다고 가정합니다.)

 이 예제의 수행 결과는 이전 예제와 동일하게 출력됩니다.

 ------------------------------------------------
 * Sequence 에 대한 설명은 아래 링크를 참조하세요.
   = https://typealias.com/guides/when-to-use-sequences/

 */