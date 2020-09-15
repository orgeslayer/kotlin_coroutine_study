package toc4

import kotlinx.coroutines.*

class Activity {
    private val mainScope = CoroutineScope(Dispatchers.Default) // use Default for test purposes

    fun destroy() {
        mainScope.cancel()
    }

    fun doSomething() {
        // launch ten coroutines for a demo, each working for a different time
        repeat(10) { i ->
            mainScope.launch {
                delay((i + 1) * 200L) // variable delay 200ms, 400ms, ... etc
                println("Coroutine $i is done")
            }
        }
    }
} // class Activity ends

fun main() = runBlocking {
    val activity = Activity()
    activity.doSomething() // run test function
    println("Launched coroutines")
    delay(500L) // delay for half a second
    println("Destroying activity!")
    activity.destroy() // cancels all coroutines
    delay(1000) // visually confirm that they don't work
}

/**
 * Coroutine scope
 *

 Context, 자식(서브루틴), Jobs 을 함께 생각해 봅시다.
 우리의 어플리케이션이 라이프사이클을 갖지만 코루틴이 아닌 객체가 있다고 가정해 봅시다.
 안드로이드 어플리케이션을 작성하면서 비동기적으로 데이터를 가져오거나 업데이트하고,
 애니메이션 처리를 하는 등 다양한 코루틴을 Activity 에서 실행하는 것을 예로 들 수 있습니다.
 이러한 모든 코루틴들은 반드시 Activity가 종료될 때 메모리 누수를 방지하기 위하여 취소되어야 합니다.
 물론 우리는 수동으로 context와 job을 activity와 라이프사이클간 연결하여 조작이 가능하지만,
 kotlinx.coroutines 은 추상화된 캡슐을 제공합니다 : CoroutineScope
 이미 CoroutineScope 는 extensions 으로 제공되는 코루틴 빌더를 통해 친숙할 것입니다.

 우리는 코루틴들을 CoroutineScope 의 인스턴스로 만들어서 코루틴의 라이프사이클을
 Activity의 라이프사이클과 연결하여 관리할 수 있습니다. CoroutineScope 객체는
 CoroutineScope() 또는 MainScope() 팩토리함수를 통해 생성될 수 있습니다.
 전자( =CoroutineScope() )의 경우 범용적으로 사용되기 위한 목적으로 생성하는 반면,
 후자( =MainScope() )의 경우 UI애플리케이션을 위해 만들고 기본 디스패처로 Dispatchers.Main 을 사용합니다.

 이제 Scope를 활용하여 Activity 내에 코루틴을 실행(launch)할 수 있습니다.
 예제 코드에서 Activity 는 10개의 코루틴이 각각 다른 시간동안 지연되는 동작을 실행(launch)합니다.

 메인 함수에서는 activity 인스턴스를 생성하고, 테스트를 위한 doSomething() 을 호출한 후,
 500ms 이 후 activity 를 종료(=destroy)합니다. 이것은 doSomething() 에서 실행시킨 모든
 코루틴을 취소할 수 있습니다. 메인에서 조금 더 기다려 주었음에도 불구하고 activity 가 종료(=destroyed)
 되었기 때문에 이러한 사실을 확인할 수 있습니다.

 예제 코드를 실행시키면 다음과 같은 결과를 확인할 수 있습니다.

 --------------------------------------------------
    Launched coroutines
    Coroutine 0 is done
    Coroutine 1 is done
    Destroying activity!
 --------------------------------------------------

 보는 것 처럼 처음 두 개의 코루틴만 메시지를 출력하고, 나머지는 Activity.destory() 에서 job.cancel()
 한번의 호출로 취소되게 됩니다.

 참고로 안드로이드는 1차적으로 라이프사이클을 가지고 있는 요소들에 대하여 scope 를 지원하고 있습니다.

 --------------------------------------------------
 * 안드로이드에서 Scope 활용은 KTX extensions 를 참조하세요.
    https://developer.android.com/topic/libraries/architecture/coroutines#lifecyclescope
 */


