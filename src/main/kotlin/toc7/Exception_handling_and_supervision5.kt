package toc7

import kotlinx.coroutines.*
import java.io.IOException

fun main() = runBlocking {
    val handler = CoroutineExceptionHandler { _, exception ->
        println("CoroutineExceptionHandler got $exception with suppressed ${exception.suppressed.contentDeepToString()}")
    }
    val job = GlobalScope.launch(handler) {
        launch {
            try {
                delay(Long.MAX_VALUE) // it gets cancelled when another sibling fails with IOException
            } finally {
                throw ArithmeticException() // the second exception
            }
        }
        launch {
            delay(100)
            throw IOException() // the first exception
        }
        delay(Long.MAX_VALUE)
    }
    job.join()
}

/**
 * - Exceptions aggregation (1)
 *

 여러 자식 코루틴에서 예외로 실패되는 상황이라면, "첫 번째 예외가 이긴다."는 일반 규칙에 따라
 첫 번째 예외를 (CoroutineExceptionHanlder으로) 처리되어지게 됩니다. 첫 예외 발생 후
 추가적으로 발생되는 모든 예외는 첫 번째의 예외의 supressed 중 하나로 추가되게 됩니다.

 이 예제는 다음과 같이 출력됩니다.

 ------------------------------------
    CoroutineExceptionHandler got java.io.IOException with suppressed [java.lang.ArithmeticException]
 ------------------------------------

 Note) 이러한 케머니즘으로 동작하는 것은 오직 Java 1.7 이상 버전에서만 동작됩니다.
 JS 와 네이티브에서는 제한적인 상황이지만 앞으로 해결될 예정입니다.

 */