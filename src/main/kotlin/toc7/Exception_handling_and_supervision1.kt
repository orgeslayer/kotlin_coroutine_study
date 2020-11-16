package toc7

import kotlinx.coroutines.*

fun main() = runBlocking {
    val job = GlobalScope.launch { // root coroutine with launch
        println("Throwing exception from launch")
        throw IndexOutOfBoundsException() // Will be printed to the console by Thread.defaultUncaughtExceptionHandler
    }
    job.join()
    println("Joined failed job")
    val deferred = GlobalScope.async { // root coroutine with async
        println("Throwing exception from async")
        throw ArithmeticException() // Nothing is printed, relying on user to call await
    }
    try {
        deferred.await()
        println("Unreached")
    } catch (e: ArithmeticException) {
        println("Caught ArithmeticException")
    }
}

/**
 * Exception Handling
 * - 이번 장에서는 예외 처리 및 예외에 따른 취소에 대하여 확인합니다.
 * 우리는 이미 중단된 곳에서(suspension points) 취소할 경우 CancellationException 이
 * 발생하며, 코루틴의 메커니즘에 의해 무시되는것을 알고 있습니다. 이 장에서는 취소 중에 예외가 발생하거나,
 * 동일한 코루틴의 여러 자식 코루틴에서 예외를 발생하는 경우에 대하여 살펴봅니다.
 *
 * - Exception Propagation
 *

 코루틴 빌더는 예외상황을
 - 자동적으로 외부로 전파(propagation)하거나 > launch, actor
 - 사용자에게 노출(exposing)시키는 것 > async, produce
 두 가지 맛이 있습니다. (=두 가지 타입으로 나뉜다 정도로 해석 가능합니다.)

 다른 코루틴의 자식 코루틴이 아닌 코루틴을 만들 때,
 이전(propagation 되는 코루틴) 빌더들은 예외가 잡히지 않고 처리하며,
 자바의 Thread.uncaughtExceptionHandler 와 유사하게
 최종 예외처리를 유저가 처리될 수 있도록 하기도 합니다.
 이와 관련된 대표적인 예로, await 또는 receive 가 해당됩니다.
 (produce, receive 는 Channels 섹션에서 다룹니다.)

 위와 관련된 내용은 GlobalScope 를 사용하여 루트 코루틴을 생성하는 간단한 예제로
 증명이 가능합니다. 예제코드는 다음과 같이 출력됩니다.

 ------------------------------------
    Throwing exception from launch
        Exception in thread "DefaultDispatcher-worker-1 @coroutine#2" java.lang.IndexOutOfBoundsException
    Joined failed job
    Throwing exception from async
    Caught ArithmeticException
 ------------------------------------
 */