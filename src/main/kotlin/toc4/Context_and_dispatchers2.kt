package toc4

import kotlinx.coroutines.*

fun main() = runBlocking {
    launch(Dispatchers.Unconfined) { // not confined -- will work with main thread
        println("Unconfined      : I'm working in thread ${Thread.currentThread().name}")
        delay(500)
        println("Unconfined      : After delay in thread ${Thread.currentThread().name}")
    }
    launch { // context of the parent, main runBlocking coroutine
        println("main runBlocking: I'm working in thread ${Thread.currentThread().name}")
        delay(1000)
        println("main runBlocking: After delay in thread ${Thread.currentThread().name}")
    }
    Unit
}


/**
 * Unconfined vs confined dispatcher
 *

 Dispatcher.Unconfined 코루틴 디스패처는 호출한 스레드에서부터 실행하지만,
 첫 번째 중단점(중단 함수 호출)을 만날 때 까지만 그렇습니다. 중단점 이 후 코루틴이 재개될 때는
 중단 함수를 재개한 스레드에서 수행됩니다. Unconfined Dispatcher 는 CPU 시간을 소비하지 않거나,
 한정적으로 공유된 데이터를 업데이트(=UI와 같은) 하지 않는 특정 스레드에 국한된 작업이 아닌 경우 적절합니다.

 디스패처의 경우, 외부의 CoroutineScope 로부터 상속되는 것이 기본 동작입니다.
 runBlocking 코루틴의 기본 디스패처는 호출된 스레드에 국한되기 때문에,
 디스패처를 상속하는 것은 예측 가능한 FIFO 스케쥴링이 가능하므로 이 스레드의 실행을 국한시키는데 효과가 있습니다..

 예제코드는 다음과 같이 출력됩니다.
 --------------------------------------------------------
    Unconfined      : I'm working in thread main
    main runBlocking: I'm working in thread main
    Unconfined      : After delay in thread kotlinx.coroutines.DefaultExecutor
    main runBlocking: After delay in thread main
 --------------------------------------------------------

 runBlocking{...} 의 코루틴 컨텍스트를 상속받은 코루틴은 메인 스레드에서 실행되는 반면,
 Unconfined 코루틴 컨텍스트는 delay 함수를 사용하는 default executer 스레드에서 다시 시작됩니다.

 Note)
  Unconfined dispatcher 는 코루틴의 일부 작업이 즉시 수행되어야 할 경우 스레드 전환을 위해
  코루틴이 디스패치되어 나중에 실행되는 것이 불합리 하거나 그렇게 실행될 경우 원치않는 사이드 이펙트가 발생되기 때문에,
  특수한 상황에 도움이 되는 향상된 메커니즘입니다.
  일반 코드에서는 unconfined dispatcher 에서는 사용되면 안됩니다.
 */