package toc5

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

fun main() = runBlocking {
    // Convert an integer range to a flow
    (1..3).asFlow().collect { value -> println(value)}
}

/**
 * Flow builders
 *

 앞 얘제들에서 확인한 flow {...} 빌더는 가장 기본적인 flow builder 중 하나입니다.
 여기 flow를 좀 더 쉽게 선언하기 위한 다른 빌더들이 있습니다 :

 - 고정된 값 집합을 flow emitting 하는 flowOf 빌더 (=고정된 값 집합)
 - 다양한 컬렉션과 시퀀스를 flow로 변환해주는 .asFlow() 확장함수

 따라서, 숫자 1부터 3까지 출력하는 예제코드는 다음과 같이 작성이 가능합니다.

 */