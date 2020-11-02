package toc4

import kotlinx.coroutines.*

fun main() = runBlocking {
    launch { // context of the parent, main runBlocking coroutine
        println("main runBlocking      : I'm working in thread ${Thread.currentThread().name}")
    }
    launch(Dispatchers.Unconfined) { // not confined -- will work with main thread
        println("Unconfined            : I'm working in thread ${Thread.currentThread().name}")
    }
    launch(Dispatchers.Default) { // will get dispatched to DefaultDispatcher
        println("Default               : I'm working in thread ${Thread.currentThread().name}")
    }
    launch(newSingleThreadContext("MyOwnThread")) { // will get its own new thread
        println("newSingleThreadContext: I'm working in thread ${Thread.currentThread().name}")
    }
    Unit
}


/**
 * Coroutine Context and Dispatchers
 *  - 코루틴은 코틀린 표준 라이브러리에서 CoroutineContext 타입으로 정의된 몇몇 컨텍스트에서 수행됩니다.
 *  - 코루틴 컨텍스트는 다양한 요소의 집합입니다.
 *  - 주요 요소로는 이전에 확인했던 Job이라는 코루틴과, 이번 절에서 다루게 되는 Dispatcher 입니다.
 *
 * Dispatchers and threads
 *

 코루틴 컨텍스트는 관련 코루틴들이 실행 시 사용할 스레드 혹은 스레드풀을 결정짓는 코루틴 디스패처를 포함합니다.
 코루틴 디스패처는 코루틴의 실행을 특정 스레드로 제한하거나, 특정 스레드풀로 전달하거나,
 혹은 스레드의 제한 없이 실행되도록 할 수 있습니다.

 launch{...}, async{...} 같은 모든 코루틴 빌더를 사용할 때 선택적으로
 CoroutineContext 를 파라미터로 전달할 수 있으며, 새로운 코루틴들을 위한 디스패처나
 그 이외의 컨텍스트 요소들을 지정할 수 있습니다. 다음 예제 코드를 확인하세요.

 ------------------------------------------------------
    Unconfined            : I'm working in thread main
    Default               : I'm working in thread DefaultDispatcher-worker-1
    newSingleThreadContext: I'm working in thread MyOwnThread
    main runBlocking      : I'm working in thread main
 ------------------------------------------------------

 launch{...} 을 매개변수 없이 사용할 때는 해당 코루틴을 실행 시킨
 CoroutineScope 의 컨텍스트(즉, 디스패처)를 상속합니다. 예제 코드에서는
 메인 스레드에서 실행되는 메인 runBlocking 코루틴의 컨텍스트를 상속받습니다.

 Dispatchers.Unconfined 메인 스레드에서 동작하지만,
 사실 약간 다른 메커니즘으로 동작되는 특별한 Dispatcher 입니다.
 자세한 내용은 후에 설명합니다.

 Dispatcher.Default 는 코루틴이 GlobalScope 에서 실행될 경우에 사용히며
 공통으로 사용되는 백그라운드 스레드풀을 이용합니다.
 따라서, launch(Dispatchers.Default){...} 과 GlobalScope.launch{...} 는
 동일한 디스패처를 사용합니다.

 newSingleThreadContext 는 코루틴을 실행시키기 위한 스레드를 생성합니다.
 이 코루틴을 위한 스레드(=전용 스레드)는 매우 리소스 비용이 큽니다.
 실제 어플리에케이션에서는 close 함수를 활용하여 반드시 해제하거나,
 최상위 변수에 저장하여 어플리케이션 전체에 걸쳐 사용해야 합니다.
 */