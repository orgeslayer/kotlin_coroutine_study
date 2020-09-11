package toc4

import kotlinx.coroutines.*

fun main() = runBlocking {
    // launch a coroutine to process some kind of incoming request
    val request = launch {
        // it spawns two other jobs, one with GlobalScope
        GlobalScope.launch {
            println("job1: I run in GlobalScope and execute independently!")
            delay(1000)
            println("job1: I am not affected by cancellation of the request")
        }
        // and the other inherits the parent context
        launch {
            delay(100)
            println("job2: I am a child of the request coroutine")
            delay(1000)
            println("job2: I will not execute this line if my parent request is cancelled")
        }
    }
    delay(500)
    request.cancel() // cancel processing of the request
    delay(1000) // delay a second to see what happens
    println("main: Who has survived request cancellation?")
}

/**
 * Children of a coroutine
 *

 코루틴이 다른 코루틴의 CoroutineScope 에서 실행되면, 해당 코루틴은 부모 코루틴의
 CoroutineScope.coroutineContext 를 통해 컨텍스트를 상속 받으며,
 새로운 코루틴의 Job 역시 부모 코루틴의 Job 의 자식이 됩니다. 부모 코루틴이 취소될 때,
 모든 자식들 또한 재귀적으로 취소됩니다.

 그러나, GlobalScope 을 활용하여 코루틴을 launch 하면 새로운 코루틴의 작업은 부모가 없습니다.
 그러므로, GlobalScope 에서 실행된 코루틴은 launch 되어진 스코프와 독립적으로 운용됩니다.

 예제 코드를 실행하면 다음과 같은 결과를 확인 할 수 있습니다.

 --------------------------------------------------
    job1: I run in GlobalScope and execute independently!
    job2: I am a child of the request coroutine
    job1: I am not affected by cancellation of the request
    main: Who has survived request cancellation?
 --------------------------------------------------

 */

//fun main() = runBlocking {
//    // launch a coroutine to process some kind of incoming request
//    val request = launch {
//        // it spawns two other jobs, one with GlobalScope
//        GlobalScope.launch {
//            println("job1: I run in GlobalScope and execute independently!")
//            delay(1000)
//            println("job1: I am not affected by cancellation of the request")
//        }
//        // and other inherits the parent context
//        launch {
//            delay(100)
//            println("job2: I am a child of the request coroutine")
//            delay(1000)
//            println("job2: I will not execute this line if my parent request is cancelled")
//        }
//
//        // and the others inherits the parent context
//        launch {
//            delay(300)
//            println("job3: I am a child of the request coroutine")
//            delay(1000)
//            println("job3: I will not execute this line if my parent request is cancelled")
//        }
//    }
//    delay(500)
//    request.cancel() // cancel processing of the request
//    delay(1000) // delay a second to see what happens
//    println("main: Who has survived request cancellation?")
//}

