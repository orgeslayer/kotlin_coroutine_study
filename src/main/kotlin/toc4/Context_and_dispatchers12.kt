package toc4

import kotlinx.coroutines.*

//val threadLocal = ThreadLocal<String?>() // declare thread-local variable

fun main() = runBlocking {
    threadLocal.set("main")
    println("Pre-main, current thread: ${Thread.currentThread()}, thread local value: '${threadLocal.get()}'")
    val job = launch(Dispatchers.Default + threadLocal.asContextElement(value = "launch")) {
        println("Launch start, current thread: ${Thread.currentThread()}, thread local value: '${threadLocal.get()}'")
        yield()
        println("After yield, current  thread: ${Thread.currentThread()}, thread local value: '${threadLocal.get()}'")

        withContext(threadLocal.asContextElement(value = "child")) {
            println("Child : Launch start, current thread: ${Thread.currentThread()}, thread local value: '${threadLocal.get()}'")
            yield()
            println("Child : After yield, current thread: ${Thread.currentThread()}, thread local value: '${threadLocal.get()}'")
        }
    }
    job.join()
    println("Post-main, current thread: ${Thread.currentThread()}, thread local value: '${threadLocal.get()}'")
}

/**
 * Thread-local data (2)
 *  - 앞의 내용과 이어집니다.
 *

 ThreadLocal 은 first-class 를 지원하며, kotlinx.coroutines 에서 제공되는
 어떠한 원시타입(primitive)이라도 사용이 가능합니다. 한 가지 중요한 제약사항이 있는데,
 thread-local 값이 변경될 경우 새로운 값은 코루틴 호출자에게 전파되지 않으며,
 업데이트 된 값은 다음 중단점에서 잃어버린 다는 것입니다. (컨텍스트의 요소가 모든 ThreadLocal
 객체에 접근하는 것을 추적하지 않기 때문에) 코루틴에서 ThreadLocal 값을 변경하기 위해서는
 withContext를 사용하면 됩니다. 좀 더 자세한 내용은 asContextElement를 참조하세요.

 대신, 값을 class Counter(var i: Int) 와 같은 변경 가능한 오브젝트로 Boxing 하여
 ThreadLocal 에 저장 할 수 있습니다. 하지만, 이 경우에는 Boxing 되어있는 값에
 동시에 접근할 경우에 대한 동기화 문제에 대하여 직접 처리를 해주어야 합니다.

 로딩 MDC 와의 통합이나 transactional context, 내부적으로 ThreadLocal 을 이용하는
 라이브러리와 같은 경우 등 좀 더 고급목적으로 활용하기 위해서는 ThreadContextElement
 인터페이스를 를 구현하는 문서를 참조하시면 됩니다.

 --------------------------
 * ThreadContextElement
   - https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-thread-context-element/index.html

 * MDC(Mapping Diagnostic Context) 에 대한 참조 내용은 아래 문서를 참조하세요.
   - https://bcho.tistory.com/1316

 */


