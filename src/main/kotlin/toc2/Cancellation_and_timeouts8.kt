package toc2

import kotlinx.coroutines.*

var acquired = 0

class Resource {
   init { acquired++ } // Acquire the resource
   fun close() { acquired-- } // Release the resource
}

fun main() {
   runBlocking {
      repeat(100_000) { // Launch 100K coroutines
         launch {
            val resource = withTimeout(60) { // Timeout of 60 ms
               delay(50) // Delay for 50 ms
               Resource() // Acquire a resource and return it from withTimeout block
            }
            resource.close() // Release the resource
         }
      }
   }
   // Outside of runBlocking all coroutines have completed
   println(acquired) // Print the number of resources still acquired
}

/**
 * Asynchronous timeout and resources (1)
 *

 withTimeout 의 타임아웃 이벤트는 실행되는 코드 블록(=부모 루틴)에 대하여 비동기적이며,
 타임아웃 코드블록 수행 중 언제든지 발생될 수 있습니다. 코드 블록 내에서 자원을 열거나 획득할 경우,
 이 점을 명심하세요.

 예를들어, 자원을 획득하면 카운터를 증가시키고 종료 함수를 호출하면 카운터를 감소시키켜
 얼마나 생성되었는지 추적이 가능한, 즉 자원 획득 및 종료를 모방하는 Resource 클래스가 있습니다.
 withTimeout 코드블록 내부에서 약간의 지연(delay) 후 이 리소스를 획득하며,
 코드블록 밖에서 해제하는 코루틴을 약간의 timeout을 설정하여 대량으로 실행시켜 봅시다.

 예제 코드를 실행해 보면, 실행 결과 0이 아닌 값을 확인하기 위해 timeout 의 타이밍을 조정하여
 항상 0이 출력되지 않는 것을 확인할 수 있습니다.

 Note) 참고로 이 예제는 동일한 메인 스레드에서 발생시키므로
 100_000 회 코루틴 실행에서 acquired 카운터를 증가 및 감소시키는 것은 완전히 안전합니다.
 이와 관련하여 좀 더 자세한 내용은 다음 챕터인 Coroutine Context 에서 설명합니다.

 */
