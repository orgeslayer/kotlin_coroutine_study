package toc5

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import kotlin.system.measureTimeMillis

fun main() = runBlocking {
    val startTime = System.currentTimeMillis() // remember the start time
    (1..3).asFlow().onEach { delay(100) } // a number every 100 ms
            .flatMapConcat { requestFlow(it) } // see asynchronous_flow23.kt
            .collect { value -> // collect and print
                println("$value at ${System.currentTimeMillis() - startTime} ms from start")
            }
}

/**
 * Flattening flows
 * - flatMapConcat
 *

 연결 모드(=concatening)는 flatMapConcat, flattenConcat 연산자로 구현되어 있습니다.
 이것들은 시퀀스 연산자들과 가장 유사하게 동작됩니다. 다음 예제에서 확인할 수 있듯이,
 내부 flow 가 종료될 때 까지 다음 flow 가 시작을 대기하고 있습니다.

 -----------------------------------------
    1: First at 130 ms from start
    1: Second at 631 ms from start
    2: First at 732 ms from start
    2: Second at 1233 ms from start
    3: First at 1337 ms from start
    3: Second at 1837 ms from start
 -----------------------------------------

 flatMapConcat 연산자의 순차적인 특징은 출력 결과로 확인할 수 있습니다.

 */