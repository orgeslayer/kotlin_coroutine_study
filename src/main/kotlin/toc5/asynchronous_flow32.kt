package toc5

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import kotlin.system.measureTimeMillis

fun simpleFinally() = (1..3).asFlow()

fun main() = runBlocking {
    try {
        simpleFinally().collect { value -> println(value) }
    } finally {
        println("Done")
    }
}

/**
 * Flow completion
 * - flow 수집 활동이 종료될 때 (일반적으로 혹은 예외적으로) 어떤 동작 처리가 필요할 수 있습니다.
 * 이미 알아차리셨겠지만, 두 가지 방법으로 처리 가능합니다: 명령적(imperative) or 선언적(declarative)
 * - Imperative finally block
 *

 try/catch 구문 외에도, collector 는 또한 수집 동작이 완료된 후 finally 구문을
 실행합니다. 예제 코드는 simpleFinally 로부터 생성된 flow 가 3개의 숫자를 생성한 이 후
 "Done" 문자열를 출력합니다.

 ----------------------------------------
    1
    2
    3
    Done
 ----------------------------------------

 */