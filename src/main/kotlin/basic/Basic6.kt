package basic

import kotlinx.coroutines.*

fun main() = runBlocking { // this: CoroutineScope
    launch {
        delay(2000L)
        println("Task from runBlocking")
    }

    coroutineScope { // Creates a coroutine scope
        launch {
            delay(5000L)
            println("Task from nested launch")
        }

        delay(1000L)
        println("Task from coroutine scope") // This line will be printed before the nested launch
    }

    println("Coroutine scope is over") // This line is not printed until the nested launch completes
}

/**
 * Scope builder
 *

 추가적으로 코루틴 스코프 내 사용자 정의 스코프가 필요하다면, coroutineScope{ } 을 사용할 수 있습니다.
 이 빌더로 생성된 코루틴 블록 내 자식 코루틴들(launch)이 끝나기 전 까지 종료되지 않도록 정의할 수 있습니다.

 runBlocking과 coroutineScope 두 가지 모두 자식 코루틴이 끝날때 까지 유지되는 부분이 유사해 보입니다.
 가장 큰 차이점은 runBlocking{ } 은 코루틴 실행 시 현재 스레드를 대기하는 것인 반면에,
 coroutineScope{ } 은 그냥 중단됩니다. 이러한 차이때문에,
 runBlocking은 일반 함수이고, coroutineScope 는 suspend 함수입니다.

 예제코드를 실행하면 다음과 같은 순서로 출력이 됩니다.
 - Task from coroutine scope
 - Task from runBlocking
 - Task from nested launch
 - Coroutine scope is over
-------------------------------------------
 * kotlin 에서 구현되는 일반적인 함수를 가이드 문서에서는 regular function 으로 표현하고 있습니다.
 이 후부터 regular function 은 일반 함수로 표현합니다.
*/