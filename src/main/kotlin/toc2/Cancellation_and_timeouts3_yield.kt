package toc2

import kotlinx.coroutines.*

fun main() = runBlocking {
    launch {
        repeat(3) {
            println("first job: work ${it+1}")
            yield()
        }
    }

    launch {
        repeat(3) {
            println("second job: work ${it+1}")
            yield()
        }
    }

    delay(300L)
    println("end")
}

/**
 * (Extend) Making computation code cancellable
 * - Coroutine yield()
 *

 코루틴을 정지시키려면 yield() 함수를 호출하면 됩니다.
 yield() 가 호출되면 dispatcher 가 다음에 수행되어야 할 것을 결정합니다.
 2개의 코루틴이 launch 되어 코루틴이 실행되며, 실행 결과는 다음과 같습니다.

 -------------------------------
    first job: work 1
    second job: work 1
    first job: work 2
    second job: work 2
    first job: work 3
    second job: work 3
    end
 -------------------------------

 참조) Making computation code cancellable 가이드에서 제안하는
    yield를 활용하는 방법 예제입니다. 메인 코루틴 역시 dispatcher 가 수행되어야 될 지
    체크하는 대상이므로, `` regions 코드 를 실행하면 아래와 같은 실행 결과를 확인할 수 있습니다.

 -------------------------------
    main: start job 1
    first job: work 1
    second job: work 1
    main: start job 2
    first job: work 2
    second job: work 2
    main: start job 3
    first job: work 3
    second job: work 3
 -------------------------------
*/

// region yield() in main routine

//fun main() = runBlocking {
//    launch {
//        repeat(3) {
//            println("first job: work ${it+1}")
//            yield()
//        }
//    }
//
//    launch {
//        repeat(3) {
//            println("second job: work ${it+1}")
//            yield()
//        }
//    }
//
//    repeat(3) {
//        println("main: start job ${it+1}")
//        yield()
//    }
//}

// endregion