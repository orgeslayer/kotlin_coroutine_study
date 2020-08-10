package toc3

import kotlinx.coroutines.*
import kotlin.system.measureTimeMillis

//suspend fun doSomethingUsefulOne(): Int {
//    delay(1000L) // pretend we are doing something useful here
//    return 13
//}
//
//suspend fun doSomethingUsefulTwo(): Int {
//    delay(1000L) // pretend we are doing something useful here, too
//    return 29
//}

// The result type of somethingUsefulOneAsync is Deferred<Int>
fun somethingUsefulOneAsync() = GlobalScope.async {
    doSomethingUsefulOne()
}

// The result type of somethingUsefulTwoAsync is Deferred<Int>
fun somethingUsefulTwoAsync() = GlobalScope.async {
    doSomethingUsefulTwo()
}

// note that we don't have `runBlocking` to the right of `main` in this example
fun main() {
    val time = measureTimeMillis {
        // we can initiate async actions outside of a coroutine
        val one = somethingUsefulOneAsync()
        val two = somethingUsefulTwoAsync()
        // but waiting for a result must involve either suspending or blocking.
        // here we use `runBlocking { ... }` to block the main thread while waiting for the result
        runBlocking {
            println("The answer is ${one.await() + two.await()}")
        }
    }
    println("Completed in $time ms")
}

/**
 * Async-style functions
 *

 우리는 이전에 작성된 doSomethingUsefulOne(), doSomethingUsefulTwo() 함수를
 명시적으로 GlobalScope의 async() 코루틴빌더를 활용하여 비동기스타일의 함수로 정의가 가능합니다.
 이러한 함수의 이름을 "...Async" 접미사를 지정하여 해당 함수는 지연결과 (Deferred<T>)를
 사용하기 위함을 관례적으로 표현하여 강조할 수 있습니다.

 이러한 xxxAsync 함수는 중단함수 (suspending function)가 아닙니다.
 따라서, 어디에서든지 사용이 가능합니다. 하지만 이 함수들의 실행은
 항상 비동기적으로 실행됨을 의미하고 있습니다.
 다음 예제는 이 함수들이 코루틴이 아닌 곳에서 사용하는 예제입니다.

 | 이와같이 비동기 함수구성을 프로그래밍 스타일 예시로 제공한 이유는
 | 단지 다른 프로그래밍 언어에서 인기가 있기 스타일이기 때문입니다.
 | 코틀린 코루틴에서는 아래 설명된 내용으로 인해 크게 권장하지 않습니다.

 val one = somethingUsefulOneAsync() 라인과 one.await() 사이에 오류가 있고,
 예외가 발생하여 프로그램이 수행되고 있던 작업이 중단될 경우 어떻게 동작할지 생각해 보십시오.
 일반적으로 글로벌 에러핸들러에서는 이 예외를 캐치하여 처리할 수 있자만, (catch, exception)
 프로그램은 다른 동작을 계속 수행하게 됩니다. somethingUsefulOneAsync() 이 여전히
 백그라운드에서 실행되고 있지만, 이 비동기를 실행시킨 루틴은 종료가 되어버렸다.
 이러한 문제는 잘 구조화된 동시성 코드에서는 발생되지 않습니다.
 (다음 예제 참조)

 */