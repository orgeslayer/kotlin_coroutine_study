package toc5

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import kotlin.system.measureTimeMillis

fun main() = runBlocking {
    val time = measureTimeMillis {
        simpleBuffering()
            .buffer()   // buffer emissions, don't wait
            .collect { value ->
                delay(300) // pretend we are processing it for 300 ms
                println(value)
            }
    }
    println("Collected in $time ms")
}

/**
 * Buffering (2)
 *

 우리는 flow 의 buffer() 연산자를 활용하여 방출(emit) 코드블록과 collect 의 코드블록을
 동시에 실행할 수 있습니다.

 ------------
    1
    2
    3
    Collected in 1052 ms
 ------------

 예제코드는 첫 번째 숫자를 전달하는데 100ms 를 대기하고, 이 후부터는 각 숫자를 처리하는데
 300ms 씩 만큼 소요되도록 처리하는 파이프라인을 효과적으로 만들었기 때문에 같은 숫자를 더 빨리 만듭니다.
 이러한 방법으로 실행했을 경우 약 1000ms 정도 소요됩니다.

 * flowOn 연산자는 CoroutineDispatcher 가 변경될 때 동일한 buffering 처리 메커니즘을 사용하는 것에
 유의하세요. 예제에서는 flow 의 처리를 수행하는 context 변경 없이 명시적으로 buffer() 연산자를 사용하여 버퍼링을 수행했습니다.

 */