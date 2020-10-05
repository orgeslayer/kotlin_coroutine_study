package toc5

/**
 * Flow and Reactive Streams
 *

 Reactive Stream 이나 리액티브 프레임워크 (RxJava 나 Reactor 프로젝트 등)
 에 익숙하신 분들은 아마 flow 의 디자인이 매우 유사해 보일 것입니다. (=flow 의 설계?)

 실제로 flow 는 Reactive Stream 과 다양한 구현체에서 영감을 얻었습니다.
 하지만 flow 의 중요한 목표는 구조화된 동시성을 따르며 코틀린과 중단 함수를 이용하여
 가능한한 단순화 된 디자인을 갖는 것입니다. 이 목표를 달성하는 것은,
 Reactive 개척자들의 엄청난 성과 없이는 불가능했을 것입니다. Reactive Stream 과
 Kotlin Flow 에 대한 완전한 이야기는 아래 아티클을 통해 읽어보실 수 있습니다.
 (https://medium.com/@elizarov/reactive-streams-and-kotlin-flows-bfd12772cda4)

 개념적으로는 다르지만 Flow 는 Reactive Stream 이며, reactive Publisher 로 변환하거나
 그 반대의 경우 (=reactive Publisher 를 flow 로 변환)도 가능합니다. (스펙 및 TCK 를 준수함)
 그러한 변환기는 kotlinx.coroutins 에 의해 제공되며, 그에 대응하는 리액티브 모듈에서 찾아볼 수 있습니다.

 - kotlin-coroutines-reactive : Reactive Streams
 - kotlin-coroutines-reactor : Project Reactor
 - kotlin-coroutines-rx2 / kotlin-coroutines-rx3 : RxJava2 / RxJava3

 통합 모듈은 flow 와의 상호 변환과 Reactor context 와의 통합, 다양한 리액티브 요소들을 사용할 수 있는
 suspension 친화적인 방법이 포함되어 있습니다.

 ----------------------------------------
 * TCK - Technology Compatibility Kit 약자로, 아래 링크를 참조하세요.
   (https://github.com/reactive-streams/reactive-streams-jvm/tree/master/tck)

 */