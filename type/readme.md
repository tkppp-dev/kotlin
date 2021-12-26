포스팅 주소 : https://velog.io/@tkppp-dev/%EC%BD%94%ED%8B%80%EB%A6%B0-%ED%83%80%EC%9E%85-%EC%8B%9C%EC%8A%A4%ED%85%9C
포스팅 주소 : https://velog.io/@tkppp-dev/%EC%BD%94%ED%8B%80%EB%A6%B0-%ED%83%80%EC%9E%85-%EC%8B%9C%EC%8A%A4%ED%85%9C2

# Nullable
코틀린은 null이 될 수 있는 타입과 null이 될 수 없는 타입을 구분한다. 만약 변수가 null이 될 수 있는 가능성이 있을 경우에는 해당 변수 타입 뒤에 ?를 붙여 지정해 Nullable한 변수라는 것을 명시해야한다.
``` kotlin
val str: String? = ...
```

## 안전한 호출 연산자 ?.
nullable 타입을 지정하면 이에 대한 null 처리가 필수적이다. if로 널 처리를 핻도 되지만 이를 **?.** 연산자를 이용하여 간단하게 처리할 수 있다.

``` kotlin
val p = Person()

p?.getName()

if(p.getName() != null){
    return p.name
}
else{
    return null
}
```

## 엘비스 연산자 ?:
?.를 이용해 널처리를 쉽게 할 수 있지만 변수가 null일 경우 null만 반환한다는 문제가 있다. 이를 해결하기 위해 엘비스 연산자로 null일때 반환할 값을 지정할 수 있다.

``` kotlin
val p = Person()

p?.getName() ?: "none"

if(p.getName() != null){
    return p.name
}
else{
    return "none"
}
```
코틀린에서는 return이나 throw 등도 연산의 식이다. 따라서 엘비스 연산자의 우항에 return이나 throw 등의 연산을 집어 넣을 수도 있다.

## 안전한 캐스트 as?
코틀린에서는 **as** 키워드를 통해 캐스팅을 한다. 만약 캐스팅을 할 수 없다면 ClassCastException이 발생한다. **as?** 는 캐스팅이 불가능한 경우 예외가 아닌 null을 반환한다. 이를 엘비스 연산자와 연계해 사용할 수 도 있다.

``` kotlin
class Person(val firstName: String, val lastName: String){
    override fun equals(o: Any?): Boolean{
        val otherPerson = o as? Person ?: return false
        
        return otherPerson.firstName == firstName && otherPerson.lastName == lastName
    }
}
```

## 널 아님 단언 !!
**!!** 를 통해 Nullable 타입을 일반 타입으로 변환할 수 있다. 만약 그 변수가 null이라면 코틀린은 예외를 발생시킨다.

가급적 !!을 사용하기보다는 다른 방법을 사용하는 것이 좋다.

## let 함수
let 함수를 안전한 호출 연산자와 함께 사용하면 원하는 식을 평가해서 결과가 널인지 검사한 다음 그 결과를 변수에 넣는 작업을 할 수 있다. 수신 객체가 null이 아니라면 let 함수의 람다 인자로 그 객체를 넘긴다. null이라면 let 함수는 실행되지 않는다.

``` kotlin
fun sendEmailTo(email: String) { ... }
```
sendEmailTo( ) 함수는 널이 아닌 문자열만 인자로 넣을 수 있다. 만약 Nullable한 인자를 넣으려면 먼저 널 검사를 수행해야 한다.
``` kotlin
val email = "gowldla0423@naver.com"
if(email != null) sendEmailTo(email)
```
위 코드를 let함수를 이용해 변경 할 수 있다.
``` kotlin
"gowldla0423@naver.com"?.let { sendToEmail(it) }
```

> 만약 let을 안전한 호출 연산자 ?.와 같이 쓰지 않는다면 수신객체는 Nullable 타입으로 취급받는다.

## 나중에 초기화할 프로퍼티
코틀린에서 널이 될 수 없는 프로퍼티는 생성자 안에서 초기화 하지 않고 특별한 메소드 안에서 초기화할 수 없다. 프로퍼티의 초기화를 하지 않고 사용할려면 그 프로퍼티를 Nullable한 타입으로 선언해야한다. 하지만 프로퍼티 사용에 있어 널 체크나 !! 연산자를 사용해야만 하는 불편함이 있다. 하지만 lateinit 키워드를 이용해 널이 될 수 없는 타입의 프로퍼티도 나중에 초기화할 수 있다.

```  kotlin
class MyService {
    fun performAction(): String = "foo"
}

class MyTest {
    private var myService: MyService? = null
    
    @Before fun setUp(){
        myService = MyService()
    }
    
    @Test fun testAction() {
        Assert.assertEquals("foo", myService!!.performAction())
    }
}

// lateinit
class MyTest {
    private lateinit var myService: MyService
    
    @Before fun setUp(){
        myService = MyService()
    }
    
    @Test fun testAction() {
        // 널 체크 필요 X
        Assert.assertEquals("foo", myService.performAction())
    }
}

```
만약 lateinit 프로퍼티를 초기화하지 않고 사용하면 어떻게 될까? 이때는 lateinit property가 초기화되지 않았다는 예외가 발생한다. 런타임에 에러가 발생하지만 정확이 어디서 초기화가 되지 않았는지 알 수 있기 때문에 널포이터 예외보다 빠른 디버깅이 가능하다.

## nullable과 자바
만약 코틀린에서 null을 반환할 수 있는 함수로 변수를 초기화 한다고 생각해보자. 이 경우에도 자연스럽게 코틀린 컴파일러는 nullable 타입임을 추론 할 수 있다. 코틀린 함수에서는 null을 반환할 경우 반환 타입을 nullable로 강제하기 때문에 NullPointerException이 발생할 가능성을 컴파일 단계에서 차단한다. 문제는 자바 라이브러리를 사용하는 경우다.

``` java
public class NullableClass {
    public static String getIsNull(int n){
        if(n % 2 == 0){
            return "I'm not null";
        }
        else{
            return null;
        }
    }
}
```
```kotlin
fun getIsNullInKt(n: Int): String? = when(n % 2 == 0){
    true -> "I'm not null"
    else -> null
}

for(i in 0 until 5){
    val res1 = getIsNullInKt(i)
    val res2 = NullableClass.getIsNull(i)
    println("res1 - $i : ${res1.length}")	// 1번 - 컴파일 에러, 널 처리 필요
    println("res2 - $i : ${res2.length}")	// 2번 - 에러 X, 실행중 널포인터 에러
}
```
위의 코드에서 1번과 2번을 실행한다면 1번에서는 컴파일 에러가 뜬다. nullable한 타입임을 명시하지 않았더라도 컴파일러가 nullable한 타입임을알아채고 null 처리를 하라고 한다. 하지만 자바 코드를 사용한 2번에서는 컴파일러가 이를 잡아내지 못한다. 자바에서는 nullable 타입이 없기 때문이다. 그렇기 때문에 자바 라이브러리를 사용하는 경우 널이 될 수 있다면 스마트 캐스트를 이용한 초기화는 지양해야 한다.

``` kotlin
for(i in 0 until 5){
    val res1 = getIsNullInKt(i)
    val res2: String? = NullableClass.getIsNull(i)
    println("res1 - $i : ${res1.length}")	// 1번 - 컴파일 에러, 널 처리 필요
    println("res2 - $i : ${res2.length}")	// 2번 - 컴파일 에러, 널 처리 필요
}
```
위와 같이 변수의 타입을 nullable로 명시하면 정상적으로 컴파일러가 잡아낸다.

### 플랫폼 타입
자바에서 Nullable 타입이 없을 뿐이지 구별은 가능하다. @NotNull, @Nullable 등의 어노테이션을 통해 이를 구분할 수 있다. 하지만 그렇지 않는 경우에는 코틀린은 자바의 타입을 플랫폼 타입으로 규정한다.

플랫폼 타입은 널 관련 정보를 알 수 없는 타입을 말한다. 타입을 Nullable, NotNull로 처리하던지 컴파일러는 상관하지 않는다. 플랫폼 타입에 대해서는 책임을 사용자에 있다는 것이다. 위의 코드에서 컴파일 에러가 뜨지 않는 것이 바로 이 때문이다.

# 코틀린의 원시 타입

## 원시 타입 - Int, Boolean 등
자바에서는 원시 타입과 객체 타입을 구분한다. 원시 타입에 객체 타입의 메소드를 사용할 경우 자동 박싱을 통해 객체 타입으로 변환한 후 사용된다.

자바의 경우 원시 타입과 객체 타입을 구분하므로 제네릭을 사용할 때 원시타입의 사용이 불가능하다. Collection<int\> 가 아닌 Collection<Integer\>를 사용해야 한다. 코틀린은 이런 원시 타입과 객체 타입을 구분하지 않으므로 항상 같은 타입을 사용한다.

그렇다면 원시 타입과 참조 타입을 구분하지 않는다는 것은 사실 리터럴만 원시 타입일뿐 객체로 취급되는 것이 아닌가? 라는 의문을 가질 수 있다. 하지만 코틀린도 두 타입을 구분하기는 한다. 이를 컴파일 타임에 적절하게 구분한다. 변수, 파라미터, 반환 타입의 Int는 대부분 int 타입으로 컴파일 된다. 하지만 제네릭이나 컬렉션을 사용하는 경우 Integer로 컴파일 된다.

> **코틀린 원시 타입**
> - 정수 타입 : Byte, Short, Int, Long
> - 부동소수점 타입 : Float, Double
> - 문자 타입 : Char
> - 불리언 타입 : Boolean

코틀린의 원시 타입에는 널 참조가 들어갈 수 없기 대문에 쉽게 그에 상응하는 원시 타입으로 컴파일 할 수 있다. 반대로 자바 원시 타입을 코틀린에서 사용할 때는 플랫폼 타입이 아닌 널이 될 수 없는 타입으로 취급된다.

## 널이 될 수 있는 원시 타입 - Int?, Boolean? 등
널 참조는 자바의 참조 타입 변수에만 대입할 수 있으므로 원시 타입은 Nullable 할 수 없다. 따라서 널이 될 수 있는 원시 타입을 사용하면 자바의 래퍼 타입으로 컴파일 된다.

## 숫자 변환
코틀린에서는 자바와 같이 숫자 타입을 자동으로 변환하지 않는다. 결과 타입이 허용하는 숙자의 범위가 원래 타입보다 넓은 경우에도 불가하다.
``` kotlin
val i = 1
val l: Long = i	// 컴파일 에러
```
같은 정수 타입이지만 변환이 불가하다. 변환하고 싶다면 toType() 메소드로 변환해주어야 한다.
``` kotlin
val i = 1
val l: Long = i.toLong()
```

숫자 타입의 비교도 마찬가지다
``` kotlin
val x = 1
val list = listOf(1L, 2L, 3L)

x in list	// false
```
타입 추론을 이용하더라도 숫자 리터럴을 통해 타입을 명시하지 않아도 된다.
``` kotlin
val l = 1L	// Long
val f = 1.0f	// Float
```

# Any, Any? - 최상위 타입
자바의 Object 클래스는 모든 참조 타입의 조상이다. 즉 원시 타입은 Object 클래스의 메소드를 사용할 수 없다는 것이다. 이를 사용하기 위해서 박싱을 통해 원시 타입을 객체 타입으로 변환하여 사용한다.

코틀린은 Any가 널이 될 수 없는 모든 타입의 조상이며 이는 원시 타입도 해당된다. 그렇기 때문에 코틀린의 모든 클래스에는 toString, equals, hashCode 메소드가 들어있다. Any 클래스를 상속했기 때문이다. 하지만 Object 클래스의 다른 메소드는 존재하지 않는다. 다른 메소드를 사용하고 싶다면 Object 클래스로 형변환 후 사용해야 한다.

# Unit - 코틀린의 void
코틀린은 void 대신 Unit 타입을 사용한다. 대부분의 상황에서 void와 Unit은 큰 차이가 없다.

차이점은 void와 달리 Unit을 타입 인자로 사용할 수 있다는 것이다. Unit 클래스에 속한 값은 단 하나이며 그 이름은 Unit이다. Unit 타입의 함수는 Unit 값을 묵시적으로 반환한다. 따라서 타입 파라미터에 Unit을 사용할 수 있다.

``` kotlin
interface Processor<T>{
    fun process() : T
}

class NoResultProcessor : Processor<Unit> {
	// Unit을 제네릭 타입 파라미터로 썻기 때문에 반환 값이 필요하지 않다.
    override fun process(){
        ...
    }
}
```

# Nothing - 정상적이지 않는 종료
결코 성공적으로 값을 돌려주지 않는 반환값이라는 개념 자체가 없는 함수가 있다. Unit 타입은 void로 취급되긴 하지만 묵시적으로 Unit 값을 반환한다. 정상적이지 않은 종료를 위한 함수가 존재한다. 예를 들어 테스트 라이브러리는 fail 함수를 제공하는 경우가 많은데 fail 함수는 특별한 메세지가 들어있는 예외를 던져서 현재 테스트를 실패시킨다. 그런 함수를 분석하는 경우 함수가 정상적으로 종료되지 않는다는 사실을 알면 유용하다

``` kotlin
fun fail(message: String) : Nothing {
    throw IllegalStateException(message)
}

val address = company.address ?: fail("No Address")
```
Nothing은 아무것도 반환하지 않기 때문에 타입 파라미터나 반환 타입으로만 사용할 수 있다. 위의 코드에서 fail을 엘비스 연산자의 우항에서 예외가 발생한다는 사실을 컴파일러가 인식하고 address가 널이 될 수 없는 타입임을 추론할 수 있다.