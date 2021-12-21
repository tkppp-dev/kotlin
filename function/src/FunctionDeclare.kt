/**
 * 코틀린은 JVM 위에서 돌아가기 때문에 실행시 Filename.kt => FilenameKt 클래스로 코틀린 컴파일러가 변환시키단.
 *
 * 코틀린은 자바와 다르게 클래스 밖에서 최상위 함수를 정의할 수 있다. 즉 메소드가 아닌 최상의 함수를 사용할 수 있다.
 * 마찬가지로 일반 변수도 최상위 프로퍼티로 정의할 수 있다. 단, var, val로 선언된 프로퍼티는 자바에서 해당 프로퍼티에
 * 접근하려면 게터, 세터를 사용해야한다. 이 떄 const val을 사용하면 public static final 로 변환되고 게터없이 사용이 가능하다.
 *
 * kotlin은 식을 본문으로 하는 함수를 정의할 수 있다.
 * 식을 본문으로 하는 함수는 타입 추론을 사용할 수 있다.
 * 일반 함수에 대해서는 타입추론을 할 수 없고 리턴 타입을 지정해줘야 함.
 *
 * void : 코틀린은 void에 대응하여 Unit 타입을 사용한다. 함수의 리턴 타입을 정의하지 않고 리턴이 없으면 Unit 타입으로 추론하고
 * Unit 객체를 반환한다.
 *
 * 리턴 타입을 정의하고 = 과 블록을 같이 사용하면 컴파일 에러가 발생한다
 * 만약 리턴 타입을 생략하고 = 과 블록을 같이 사용한다면 컴파일러는 이 함수가 람다식을 반환하는 함수라고 판단한다.
 * 사실 쓸일이 거의 없으므로 쓰지 말아야한다고 생각하면 된다.
 */


fun greet() = "Hello"
fun greetType(): String = "Hello"
fun greetRegular(): String {
    return "Hello"
}

fun sayHello() = println("Hello");
fun printAddResult(a: Int, b: Int): Unit{   // Unit은 본문 함수에도 생략 가능. 컴파일러가 추론해줌
    println("a + b = ${a + b}")
}

// Unit 타입은 내부적으로 toString(), equals(), hashCode() 메소드를 가지고 있기 때문에 아래와 같이 사용이 가능하다
// 물론 출력되는 값은 Well, Kotlin.Unit 이다. 내부적으로 Unit의 toString 메소드를 호출한 것
const val _const = "const"
val prop = "Hello"
val msg = sayHello()
fun printMsg() = println("Well, $msg")

