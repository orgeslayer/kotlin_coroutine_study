package toc1

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main() = runBlocking {
    launch { doWorld() }
    println("hello,")
}

// this is your first suspending function
suspend fun doWorld() {
    delay(1000L)
    println("World!")
}

/**
 * Waiting for a job
 *

 이제, launch { } 코드 블록을 별도 함수로 분리해 봅시다.
 별도 분릭되는 함수는 suspend 키워드를 추가하여 구현합니다.

 첫 번째로 작성한 doWorld() 는 suspending function 입니다.
 suspending function 은 코루틴 내부에서 호출되는 일반 함수와 유사하지만,
 차이점은 delay() 와 같은 다른 중단함수(=suspend function)들을 호출할 수 있다는 점입니다.

 하지만 만약, 중단함수가 현재 스코프에서 수행될 코루틴빌더를 포함한다면 어떨까요?
 이 경우, suspend 키워드 만으로는 충분하지 않습니다. 위 예제에서 CoroutineScope의 확장함수로
 doWorld를 만들 수도 있지만, 이 경우 API를 더 명확하지 않게 만듭니다.
 좀 더 나은 방법으로는 명시적으로 CoroutineScope을 필드로 갖는 클래스로 만들고,
 그 클래스가 해당 suspend 함수를 갖게 하거나, 외부 클래스가 CoroutineScope를 구현하는 것입니다.

 마지막 수단으로, CoroutineScope(coroutineContext) 을 활용할 수 있습니다만,
 더 이상 실행 범위(=scope of execution of this method)를 통제할 수 없어지기 때문에,
 이 경우는 구조적으로 안전하지 않습니다. private API 만이 이 빌더를 사용할 수 있습니다.

 --------------------------------------------------
 * CoroutineScope 는 interface 이므로,
 명시적(explict) / 암시적(implict) 구현을 의미합니다.
 */