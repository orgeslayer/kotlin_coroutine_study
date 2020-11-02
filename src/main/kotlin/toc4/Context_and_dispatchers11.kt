package toc4

import kotlinx.coroutines.*

val threadLocal = ThreadLocal<String?>() // declare thread-local variable

fun main() = runBlocking {
    threadLocal.set("main")
    println("Pre-main, current thread: ${Thread.currentThread()}, thread local value: '${threadLocal.get()}'")
    val job = launch(Dispatchers.Default + threadLocal.asContextElement(value = "launch")) {
        println("Launch start, current thread: ${Thread.currentThread()}, thread local value: '${threadLocal.get()}'")
        yield()
        println("After yield, current thread: ${Thread.currentThread()}, thread local value: '${threadLocal.get()}'")
    }
    job.join()
    println("Post-main, current thread: ${Thread.currentThread()}, thread local value: '${threadLocal.get()}'")
}

/**
 * Thread-local data (1)
 *

 때때로 코루틴 간에 스레드-로컬 데이터를 전달하는 것이 유용할 수 있습니다.
 하지만 그것들은 스레드 영역 안에 포함되지 않기 때문에 스레드-로컬 데이터를 수동으로 처리할 경우
 보일러 플레이트가 생길 수 있습니다. (= 번잡한 코드로 이어질 가능성이 높습니다.)

 이러한 문제를 돕기 위하여 ThreadLocal 을 지원하기 위하여 asContextElement
 확장함수를 제공합니다. 해당 함수는 스레드-로컬 데이터를 저장하고 코루틴이 속한 컨텍스트가 변경 시
 다시 복원하는 추가적으로 컨텍스트를 생성합니다.

 실제 동작되는 확인하는것은 것은 예제코드를 보면 쉽습니다:
 예제에서는 새로운 코루틴을 백그라운드 스레드풀에서 Dispatchers.Default 를 이용하여 동작되는
 새로운 코루틴을 실행(launch)합니다. 따라서, 해당 코드는 스레드풀의 서로다른 스레드에서 실행되지만
 threadLocal.asContextElement(value = "launch") 을 사용함으로써
 지정된 스레드-로컬 변수의 값은 그대로 유지됩니다.
 따라서, 실행결과는 아래와 같습니다.

 ------------------------------------------------
    Pre-main, current thread: Thread[main @coroutine#1,5,main], thread local value: 'main'
    Launch start, current thread: Thread[DefaultDispatcher-worker-1 @coroutine#2,5,main], thread local value: 'launch'
    After yield, current thread: Thread[DefaultDispatcher-worker-1 @coroutine#2,5,main], thread local value: 'launch'
    Post-main, current thread: Thread[main @coroutine#1,5,main], thread local value: 'main'
 ------------------------------------------------


 ------------------------------------------------
 * ThreadLocal 은 한 스레드에 의해 읽고 쓰여질 수 있는 변수를 생성할 수 있도록 돕는 클래스입니다.
   자세한 내용은 링크를 참조하세요.
   (https://parkcheolu.tistory.com/17)
 * Thread 에서 yield 는 호출한 스레드가 실행 대기상태로 돌아가며,
   다른 우선순위의 스레드가 실행할 수 있는 기회를 가질 수 있도록 해줍니다.

 */


