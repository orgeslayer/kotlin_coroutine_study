package toc1

import kotlinx.coroutines.*

fun main() {
    GlobalScope.launch { // launch a new coroutine in background and continue
        delay(1000L) // non-blocking delay for 1 second (default time unit is ms)
        println("World!") // print after delay
    }
    println("Hello,") // main thread continues while coroutine is delayed
    Thread.sleep(2000L) // block main thread for 2 seconds to keep JVM alive
}

/**
 * Basics
 *  - 이 장에서는 코루틴의 기본 개념을 다룹니다.
 *
 * Your first coroutine
 *

 해당 코드는
    Hello,
    World!
 를 출력하는 프로그램 입니다.

 본질적으로, 코루틴은 light-weighted thread 로 표현할 수 있습니다.
 샘플코드의 경우 GlobalScope.launch{ } 코루틴 빌더를 통해 코드블록이 실행하는데요.
 GlobalScope.launch 코드블록을 thread{} 로, delay(...) 대신 Thread.sleep(...) 로 변경한 뒤 실행해도 동일한 결과를 확인할 수 있습니다.

 만약 GlobalScope.launch 를 thread{} 로 변경할 경우, 컴파일러는 아래와 같은 에러를 발생 시킵니다.
 - Error: Kotlin: Suspend functions are only allowed to be called from a coroutine or another suspend function
 그 이유는 delay 함수에 있는데, 해당 함수는 suspend function 으로 스레드에 종속되어 동작할 수 없고 코루틴에서만 종속되어 동작이 가능하기 때문입니다.

 -------------------------------------------
 참조1) coroutine 은 Cooperative Routine 의 약자입니다.

 참조2) suspend : 매달다, 걸다.
 - 원 의미보다 다양하게 파생된 의미로 더 많이 사용되는 동사입니다.
 - '매달려서' 나아가지 못하기 때문에 '정지되다, 보류되다' 같은 의미로 확산되어 사용됩니다.
*/