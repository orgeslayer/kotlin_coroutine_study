package basic

import kotlinx.coroutines.*

fun main() = runBlocking { // start main coroutine
    val job = GlobalScope.launch { // launch a new coroutine in background and continue
        delay(1000L)
        println("World!")
    }
    println("Hello,")
    job.join() // wait until child coroutine completes
}

/**
 지금까지 예제는 모두 임의의 지연을 줘서 동작되도록 예제코드를 작성하였는데요,
 이러한 접근 방식은 좋지 못합니다.
 GlobalScope.launch{ } 코루틴 빌더를 통해 생성한 Job 인스턴스를 활용할 수 있습니다.

 생성된 Job 인스턴스의 job.join() 을 실행하면 현재 코루틴과 연결되어,
 해당 코루틴이 종료될 때 까지 대기한 후 종료됩니다.
 -------------------------------------------
 * 임의의 지연을 사용할 경우, 내부적으로 실행되는 코루틴들이 작업 완료하고 종료될 때 까지
   부모(코루틴)가 얼마나 대기해야할 지 예측할 수 없기 때문입니다.
 */