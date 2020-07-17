package basic

import kotlinx.coroutines.*

fun main() {
    GlobalScope.launch { // launch a new coroutine in background and continue
        delay(1000L)
        println("World!")
    }
    println("Hello,") // main thread continues here immediately
    runBlocking {     // but this expression blocks the main thread
        delay(2000L)  // ... while we delay for 2 seconds to keep JVM alive
    }
}

/**
 첫 번째 예제의 경우 지연 처리를 위하여
 non-blocking delay(...) 와 blocking Thread.sleep(...) 을 혼용되어 사용되고 있다.
 이럴 경우, 소스코드 내 어디에서 블록상태를 발생시키고 있는지 확인이 어려워 질 수 있다.

 예제 코드는 runBlocking{ } 코루틴 빌더를 이용하여 delay(...) 을 활용,
 non-blocking 만 활용하여 실행 결과를 확인할 수 있다.
 그리고 메인스레드는 runBlocking{ } 코드블록이 끝날때 까지 블록 된다.

 -------------------------------------------
 non-blocking과 blocking 을 나누는 기준은 스레드에 blocking 으로 이해하면 좋습니다.
*/