package toc5

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import kotlin.system.measureTimeMillis

fun simpleCatch(): Flow<String> = flow {
    for (i in 1..3) {
        println("Emitting $i")
        emit(i) // emit next value
    }
}.map { value ->
    check(value <= 1) { "Crashed on $value" }
    "string $value"
}

fun main() = runBlocking {
    simpleCatch()
            .catch { e -> emit("Caugh ${e.message}") } // emit on exception
            .collect { value -> println(value)}
}

/**
 * Flow exceptions
 * - Exception transparency
 *

 그렇다면, 방출되는 코드(=수집되는 코드)가 어떻게 예외 처리 동작을 캡슐화 할 수 있을까요?

 flow 는 예외 처리에 대하여 반드시 투명해야 하고(transparency),
 flow 코드 블록 내에서 try/catch 블록으로 예외 처리한 후 값을 방출하는 것은
 투명성을 위반하는 것입니다. 수잡기 측에서 예외를 던지는 것은 항상 예외를 수집할 수 있고,
 try/catch 에서 그러한 예제를 통해 보장되는 것을 확인했습니다.

 방출가(emitter) 측에서는 catch 연산자를 사용하여 예외 투명성을 보장할 수 있으며,
 예외처리에 대하여 캡슐화가 가능합니다. catch 연산자의 코드블록은 어떤 예외를 포착(=caught)
 했는지에 따라 다르게 대응이 가능합니다.

 - throw 를 이용하여 다시 예외를 전달 수 있습니다.
 - catch 로직에서 emit 을 사용하여 값으로 방출 할 수 있습니다.
 - 다른 코드를 통해 무시하거나, 기록(logging), 기타 처리가 가능합니다.

 예외를 포착했을 때 텍스트를 방출하는 예제를 확인해 봅시다.
 ----------------------------------------
    Emitting 1
    string 1
    Emitting 2
    Caugh Crashed on 2
 ----------------------------------------

 이 예제의 경우 try/catch 가 코드에 있지 않지만, 동일한 결과를 확인할 수 있습니다.

 */