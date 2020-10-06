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
 이 예제코드는 runBlocking<Unit>{...} 최상위 코루틴으로 채택함으로써 메인 함수가
 종료되는 것을 차단해주는 어답터 역할을 합니다. 명시적으로 리턴타입을 Unit 으로 지정하는데,
 코틀린에서 main 함수는 Unit 을 리턴해 줘야 하기 때문입니다.

 위의 내용을 참조하여 suspending function 을 다음과 같이 unit test 작성이 가능합니다.
 -------------------------------------------
 class MyTest {
     @Test
     fun testMySuspendingFunction() = runBlocking<Unit> {
        // here we can use suspending functions using any assertion style that we like
     }
 }
 -------------------------------------------
 * main 함수를 runBlocking{ } 빌더로 생성된 코루틴을 메인 코루틴이라고 표현하고 있으며,
   편의상 이 후부터는 메인 코루틴으로 표현됩니다.
 */