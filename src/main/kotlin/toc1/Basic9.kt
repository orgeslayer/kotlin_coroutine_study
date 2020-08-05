package toc1

import kotlinx.coroutines.*

fun main() = runBlocking {
    GlobalScope.launch {
        repeat(1000) { i ->
            println("I'm sleeping $i ...")
            delay(500L)
        }
    }
    delay(1300L) // just quit after delay
}

/**
 * Global coroutines are like daemon threads
 *

 이 코드는 오랜 시간동안 GlobalScope 에서 수행되는 코루틴을 만들어서 실행합니다.
 (500ms 간격으로 천번동안 텍스트를 출력합니다.) 실제로 실행 결과는 아래 3개 라인만 출력하고 종료됩니다.

    I'm sleeping 0 ...
    I'm sleeping 1 ...
    I'm sleeping 2 ...

 GlobalScope 에서 실행된 코루틴은 메인 함수(=runBlocking) 를 지속시키지 않습니다.
 마치 데몬 스레드와 같이 자신이 속한 프로세스가 활성화 되어있는 시간 동안만 동작합니다.
 (자신이 속한 프로세스의 종료를 종료시키지 않고 함께 종료되기 때문에 허용된 시간만큼만 동작됩니다.)
 ---------------------------------------------
 * Basic4, Basic5 내용과 함께 다시 읽어보시면 이해하는데 더 도움이 됩니다.
 */

