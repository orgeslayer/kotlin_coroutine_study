package toc1

import kotlinx.coroutines.*

fun main() = runBlocking<Unit> { // start main coroutine
    GlobalScope.launch { // launch a new coroutine in background and continue
        delay(1000L)
        println("World!")
    }
    println("Hello,") // main coroutine continues here immediately
    delay(2000L)      // delaying for 2 seconds to keep JVM alive
}

/**
 runBlocking{ } 코드블록을 Unit 을 리턴하도록 정의하면
 delay 처리가 더욱 보기좋게 수정됩니다.

 위의 내용을 참조하여 suspending function 을 다음과 같이 unit test 작성이 가능합니다.
 -------------------------------------------
 class MyTest {
     @Test
     fun testMySuspendingFunction() = runBlocking<Unit> {
        // here we can use suspending functions using any assertion style that we like
     }
 }
 -------------------------------------------
 main 함수를 runBlocking{ } 빌더로 생성된 코루틴을 메인 코루틴이라고 표현하고 있으며,
 편의상 이 후부터는 메인 코루틴으로 표현됩니다.
 */