포스팅 주소 : https://velog.io/@tkppp-dev/%EC%BD%94%ED%8B%80%EB%A6%B0%EC%9D%98-%EB%8F%99%EB%93%B1%EC%84%B1-%EB%B9%84%EA%B5%90

# 자바의 동등성

자바에서는 == 동등성 비교 연산자가 원시 타입의 비교에서는 값 비교, 객체의 비교에서는 참조값(주소)를 비교한다. 그래서 String에 대해 같은 문자열일지라도 == 로 비교하면 false이기 때문에 equals 메소드를 사용해야 된다는 것은 유명하다.
``` java
String s1 = "tkppp";
String s2 = new String("tkppp");

System.out.println("str1 == str2 : " + (s1 == s2));	// false 출력
System.out.println("str1 == str2 : " + s1.equals(s2));	// true 출력
```

위의 코드를 보면 s1과 s2는 "tkppp" 라는 동일한 문자열 값을 가짐에도 불구하고 false를 반환한다.

# 코틀린의 동등성
코틀린은 Object에 대응하는 Any 클래스에 equals가 정의되어 있고 Any 클래스는 모든 클랫의 조상이다. 따라서 equals 메소드를 사용할 수 있다.

``` kotlin
val s1 = "tkppp"
val s2 = String("tkppp".toCharArray())

println("s1 == s2 : ${s1 == s2}")	// true
println("s1 == s2 : ${s1.equals(s2)}")	// 내부적으로 수행

println("s1 === s2 : ${s1 === s2}")	// false
```
코틀린에서 == 연산자는 equals 메소드를 수행하는 관례이다. 따라서 s1 == s2 는 위의 코드와 같이 내부적으로는 s1.equals(s2)가 수행된다.

자바에서의 참조값(주소) 비교가 필요할 때도 있다. 그때는 === 연산자를 이용하면 된다.

# 문자열 리터럴 주의점
위의 코드에서 s2는 왜 문자열 리터럴을 사용하지 않고 생성자를 사용했을까? 그 이유는 문자열 리터럴로 문자열 객체를 만들시 힙 메모리 영역의 **String Constant Pool**에 저장되고 저장된 문자열 주소를 공유하기 때문이다.

![](https://images.velog.io/images/tkppp-dev/post/16bf829b-b80a-42dd-b1d0-f9fd58d2d458/image.png)

``` kotlin
val s1 = "tkppp"
val s2 = "tkppp"

println("s1 == s2 : ${s1 == s2}")	// true
println("s1 === s2 : ${s1 === s2}")	// true
```

s2를 문자열 리터럴로 생성하게 되면 둘 다 true를 반환한다. String Pool에서 같은 문자열을 참조하고 있기 때문이다.

이는 코틀린 뿐만 아니라 JVM을 사용하는 Java, Kotlin 모두 동일하다.