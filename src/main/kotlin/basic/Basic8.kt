package basic

import kotlinx.coroutines.*

fun main() = runBlocking {
    repeat(100_000) { // launch a lot of coroutines
        launch {
            delay(1000L)
            print(".")
        }
    }
}

//fun main() {
//    repeat(100_000) {
//        thread {
//            sleep(1000L)
//            print(".")
//        }
//    }
//}

/**
 * Coroutines ARE light-weight
 *

 이 코드는 십만개의 코루틴을 각각의 코루틴은 1초후에 점(.) 을 출력합니다.

 만약 같은 동작을 스레드로 실행 실행한다면, 어떤 일이 발생될까요?
 (아마 여러분의 코드는 실행 중 코드에서 Out-Of-Memory 에러가 발생 가능성이 가장 높습니다.)
-------------------------------------------
 * 하단 주석 코드 실행 결과, OutOfMemoryError 이 발생됩니다.
    Exception in thread "main" java.lang.OutOfMemoryError: unable to create new native thread
    at java.lang.Thread.start0(Native Method)
    at java.lang.Thread.start(Thread.java:717)
    at kotlin.concurrent.ThreadsKt.thread(Thread.kt:42)
    at kotlin.concurrent.ThreadsKt.thread$default(Thread.kt:25)
    at basic.Basic8Kt.main(Basic8.kt:18)
    at basic.Basic8Kt.main(Basic8.kt)
 코루틴은 스레드와는 다른, 코드 블록의 개념임을 한번 더 체크하면 좋을 거 같습니다.
 */