package toc4

import kotlinx.coroutines.*

fun main() = runBlocking<Unit> {
    launch(Dispatchers.Default + CoroutineName("test")) {
        println("I'm working in thread ${Thread.currentThread().name}")
    }
}

/**
 * Combining context elements
 *

 떼떼로 우리는 코루틴 컨텍스트를 위하여 여러 요소를 정의할 필요가 있습니다.
 + 연산자를 사용하여 그것을 해결할 수 있습니다.
 예를들어, 명시적으로 코루틴 디스패처와 코루틴 이름을 동시에 지정하여 코루틴을 실행 시킬 수 있습니다.

 -Dkotlinx.coroutines.debug 옵션으로 예제 코드를 실행하면 다음과 같이 출력됩니다.

 --------------------------------------------------
    I'm working in thread DefaultDispatcher-worker-1 @test#2
 --------------------------------------------------
 */


