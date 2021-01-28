package toc8

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.actor
import kotlinx.coroutines.sync.*

//suspend fun massiveRun(action: suspend () -> Unit) {
//    val n = 100  // number of coroutines to launch
//    val k = 1000 // times an action is repeated by each coroutine
//    val time = measureTimeMillis {
//        coroutineScope { // scope for coroutines
//            repeat(n) {
//                launch {
//                    repeat(k) { action() }
//                }
//            }
//        }
//    }
//    println("Completed ${n * k} actions in $time ms")
//}

// Message types for counterActor
sealed class CounterMsg
object IncCounter : CounterMsg() // one-way message to increment counter
class GetCounter(val response: CompletableDeferred<Int>) : CounterMsg() // a request with reply

// This function launches a new counter actor
fun CoroutineScope.counterActor() = actor<CounterMsg> {
    var counter = 0 // actor state
    for (msg in channel) { // iterate over incoming messages
        when (msg) {
            is IncCounter -> counter++
            is GetCounter -> msg.response.complete(counter)
        }
    }
}

fun main() = runBlocking {
    val counter = counterActor()
    withContext(Dispatchers.Default) {
        massiveRun {
            counter.send(IncCounter)
        }
    }
    // send a message to get a counter value from an actor
    val response = CompletableDeferred<Int>()
    counter.send(GetCounter(response))
    println("Counter = ${response.await()}")
    counter.close() // shutdown the actor
    Unit
}

/**
 * Actors
 *

 actor 는 코루틴과 이 코루틴에 제한되고 캡슐화된 상태, 다른 코루틴과 통신하기위한 채널의
 조합으로 구성되어 있습니다. 단순한 actor 의 경우 함수로 작성할 수 있지만, 좀 더 복잡한 상태의
 actor 라면 클래스로 작성하는 것이 더 적절합니다.

 actor 의 코루틴 빌더는 actor 의 메일박스 채널을 해당 스코프에서 메시지를 수신하고,
 송신 채널을 결과 작업(Job) 객체로 결합하여 actor 에 단일 참조 핸들 타입으로 전달할 수 있습니다.

 첫번째 단계는 actor 가 처리할 메시지의 클래스를 정의하는 것입니다.
 코틀린의 sealed class 는 이 목적에 가장 적절합니다.
 CounterMsg 라는 sealed class 로 카운터를 증가시키기 위하여 IncCounter 클래스와,
 카운터 값을 가져오기 위하여 GetCounter 클래스를 정의합니다.
 그리고 나서 응답을 보내야 합니다.
 CompletableDeferred 는 단일값을 나타내며 추후 알려지기(=통신되기 위한) 목적으로 사용됩니다.
 그런 다음, 우리는 actor 코루틴 빌더를 사용하여 actor 를 시작하는 함수를 정의합니다.

 예제코드에서 actor 는 어떤 context 에서 실행되는지 중요하지 않습니다.
 actor 는 코루틴이며 순차적으로 실행되기 때문에, 특정 코루틴에서 변경 가능한 공유 상태에
 대한 문제 해결책으로 실행됩니다. 실제로 actor 는 자신의 상태를 수정할 수 있지만,
 오직 메시지를 통해서만 영향을 받습니다. (lock 처리가 필요한 것을 회피할 수 있습니다.)

 이 경우 항상 해야 할 일이 있고 다른 context 로 전환할 필요가 전혀 없기 때문에
 Actor 는 부하가 걸린 상태에서 locking 하는 것 보다 효율적입니다.

 Note) actor 코루틴빌더는 produce 코루틴빌더의 이중체입니다.
 actor 는 메시지를 수신하는 채널과 관련있는 반면,
 producer 는 요소를 송신하는 채널과 관련이 있습니다.

 */