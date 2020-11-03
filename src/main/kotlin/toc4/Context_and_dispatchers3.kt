package toc4

import kotlinx.coroutines.*

fun log(msg: String) = println("[${Thread.currentThread().name}] $msg")

fun main() = runBlocking {
    val a = async {
        log("I'm computing a piece of the answer")
        6
    }
    val b = async {
        log("I'm computing another piece of the answer")
        7
    }
    log("The answer is ${a.await() * b.await()}")

    Unit
}


/**
 * Debugging coroutines and threads
 *  - 코루틴은 특정 스레드에서 실행 되었다가 다른 스레드에서 재개될 수 있습니다.
 *  단일 스레드 디스패처에서조차 코루틴이 어디서 무엇을 수행하는지 확인해 내는 것은
 *  특별한 도구사용이 없다면 확인이 어렵습니다.

 * Debugging with IDEA
 코틀린 플러그인의 코루틴 디버거는 IntelliJ IDEA 에서 코루틴 디버깅을 단순화 합니다.

 Note) 디버깅은 kotlinx-coroutines-core 버전 1.3.8 이상에서 동작됩니다.

 디버그 도구 창은 Coroutines 탭을 포함하고 있습니다. 이 탭에서는,
 현재 실행중인 코루틴과 일시 중단(suspended) 되어있는 코루틴 정보를 찾을 수 있습니다.
 코루틴들은 실행되는 디스패처에 그룹화 되어집니다.

 - 각 코루틴의 상태를 쉽게 체크할 수 있습니다.
 - 실행 중인 코루틴과 일시 중단된 코루틴의 지역 변수 및 캡쳐된 변수 를 볼 수 있습니다.
 - 전체 코루틴 생성 스택 확인이 가능하며, 코루틴 내부의 콜 스택 확인이 가능합니다.
   스택은 표준 디버깅 중 손실되는 다양한 값을 가진 프레임들을 포함하고 있습니다.

 각 코루틴의 상태와 그 스택의 전체 리포트가 필요할 경우,
 코루틴 탭 내 마우스 우측을 선택 한 뒤, Get coroutines dump 를 선택하면 됩니다.

 관련된 내용에 대한 좀 더 많은 내용은 이 블로그 포스트와 IntelliJ IDEA documents 확인해 보세요.
 > https://blog.jetbrains.com/kotlin/2020/07/kotlin-1-4-rc-debugging-coroutines
 > https://www.jetbrains.com/help/idea/debug-kotlin-coroutines.html

 * Debugging using logging
 코루틴 디버거를 사용하지 않고 어플리케이션의 스레드를 디버깅 하는 다른 방법은,
 각 로그 메시지에 스레드 이름을 출력하는 방법입니다. 이 기능은 로깅 프레임워크에서 지원합니다.
 만약 코루틴을 사용한다면, 스레드의 이름 만으로는 컨텍스트에 대한 정보를 정확히 알 수 없으며
 kotlinx.coroutines 는 디버깅 지원을 위한 도구를 포함하고 있어 좀 더 쉽게 디버깅이 가능합니다.

 'Dkotlinx.coroutines.debug' jvm 옵션을 설정하고 아래 코드를 실행 해 보세요.

 세 개의 코루틴이 있습니다. runBlock 내부의 주 코루틴(#1, 메인 코루틴) 과
 지연된 값을 처리하는 두 개의 코루틴 a(#2), b(#3) 입니다. 이 코루틴들은 모두 runBlocking
 컨텍스트에서 실행되며 메인 스레드에 국한되어 실행되어 집니다. 이 코드의 실행 결과는 아래와 같습니다.
 ------------------------------------------------------------
    [main @coroutine#2] I'm computing a piece of the answer
    [main @coroutine#3] I'm computing another piece of the answer
    [main @coroutine#1] The answer is 42
 ------------------------------------------------------------

 log 함수는 스레드 이름을 대괄호('[]') 안에 표시되며, 출력 결과를 보면
 현재 메인 스레드에서 실행 중인 것을 알 수 있고, 추가적으로 현재 실행중인 코루틴의 ID가
 출력 되는것을 확인할 수 있습니다. 이 식별자는 디버깅 모드가 켜져 있을 때 생성되는 모든 코루틴에
 연속적으로 할당됩니다.

 Note) '-ea' 옵션으로 JVM을 실행할 때도 디버깅 모드는 실행됩니다.
  디버깅 기능에 대한자세한 내용은 DEBUG_PROPERTY_NAME 속성 설명을 참조하세요.
  > https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-d-e-b-u-g_-p-r-o-p-e-r-t-y_-n-a-m-e.html

----------------------------------------

 * 만약, Dkoltinx.coroutines.debug 옵션을 켜지 않으면 기본적으로 스레드 이름만 출력됩니다.
 ------------------------------------------------------------
    [main] I'm computing a piece of the answer
    [main] I'm computing another piece of the answer
    [main] The answer is 42
 ------------------------------------------------------------

 */