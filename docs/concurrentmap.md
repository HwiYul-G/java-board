## ConcurrentMap
<p>
Hashtable은 오퍼레이션을 동기화해서 스레드 안전성을 제공한다.<br>
HashMap은 스레드 안전한 구현이 아니다.<br>
Hashtable이 스레드 안전하지만 효율적이지 않다. 완전 동기화된 Map인 Collections.synchronizedMap도 훌륭한 효율성을 보여주지 않는다.<br>
높은 동시성 상황에서 높은 처리량으로 스레드 안전하길 바라면, HashMap과 synchronizedMap은 좋지 못하다.<br>
</p>
<p>
높은 동시성 상황에서 높은 처리량을 해결하며 스레드가 안전하게 하기 위해 Java 1.5.에서 `ConcurrentMap`이 도입되었다.
</p>

- concurrency problem(동시성 문제) : 제한 없이 같은 데이터베이스에 여러 연산들이 실행될 때 발생하는 문제.
- thread-safety : data races를 피할 수 있다.
  - data races : 여러 스레드들이 데이터에 접근하고 수정하는 순서에 따라, 올바르거나 올바르지 않은 값이 데이터에 할당되는 상황

### ConcurrentMap
`ConcurrentMap`은 `Map` 인터페이스의 확장이다. `ConcurrentMap`은 스레드 안전한 처리량을 조절하는 문제를 해결하기 위해 제공된 구조와 지침이다.
몇가지 인터페이스 기본 메소드들을 재정의하며, `ConcurrentMap`은 스레드 안전하고 메모리 일관된 원자성 작업을 제공하기 위한 타당한 구현들을 위한 지침을 준다.



## 참고자료
- https://www.baeldung.com/java-concurrent-map