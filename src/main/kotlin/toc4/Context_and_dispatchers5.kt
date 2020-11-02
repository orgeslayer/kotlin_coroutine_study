package toc4

import kotlinx.coroutines.*

fun main() = runBlocking {
    println("My job is ${coroutineContext[Job]}")
}


/**
 * Job in the context
 *

 코루틴의 Job은 컨텍스트의 일부로서,
 coroutineContext[Job] 표현을 사용하여 그 코루틴의 Job 을 획득할 수 있습니다.

 디버그모드에서 실행하면, 다음과 같은 결과를 확인할 수 있습니다.

 ----------------------------------------
    My job is "coroutine#1":BlockingCoroutine{Active}@4f2410ac
 ----------------------------------------

 CoroutineScope의 isActive 는
 coroutineScope[Job]?.isActive == true
 표현의 편의를 위한 간략한 표현임을 확인해 둡시다.
 */


