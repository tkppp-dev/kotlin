포스팅 주소 : https://velog.io/@tkppp-dev/%EA%B0%9D%EC%B2%B4-%ED%81%B4%EB%9E%98%EC%8A%A4%EC%99%80-%EC%9D%B8%ED%84%B0%ED%8E%98%EC%9D%B4%EC%8A%A4

# 인터페이스
자바의 인터페이스는 상태(필드)를 가질 수 없지만 코틀린에서는 상태를 가질 수 있다. 디폴트 메소드와 같이 본문을 가진 메소드도 가능하지만 따로 키워드를 가지지 않는다. 본문이 존재하면 디폴트 메소드처럼 취급된다.

``` kotlin
interface Clickable {
    fun click() 
    fun showOff() = println("I'm clickable")
}
```

> 코틀린이 자바6를 기반으로 만들어졌기 때문에 자바8에서 도입된 디폴트 메소드로 동작하지는 않는다. 따라서 코틀린 인터페이스를 구현하는 자바 클래스는 본문이 존재하는 메소드도 구현해주어야 한다.


## 시그니처가 동일한 디폴트 메소드를 가진 인터페이스를 구현할 때

본문을 가진 인터페이스 메소드 중 시그니처가 동일한 두 인터페이스를 구현하는 클래스를 생각해보자. 두 메소드 중 하나만 본문을 가지고 있다하더라도 구현을 생략할 수 없다. 본문이 존재하더라도 메소드를 구현해주어야 한다. 그렇지 않으면 컴파일 에러가 발생한다.

``` kotlin
interface Clickable {
    fun click()
    fun showOff() = println("I'm clickable")
}

interface Focusable {
    fun showOff() = println("I'm focusable")
}

class Button : Clickable, Focusable {
    override fun click() {}
    override fun showOff() {
        println("I'm button")
    }
}

fun main(){
    val btn = Button()
    btn.showOff()	// "I'm button" 출력
}
```

그렇다면 어떻게 구현된 기존 본문을 사용할 수 있을까? super<T\>를 이용하여 기존 본문에 접근할 수 있다.

``` kotlin
class Button : Clickable, Focusable {
    override fun click() {}
    override fun showOff() {
        super<Clickable>.showOff()
        super<Focusable>.showOff()
    }
}

fun main(){
    val btn = Button()
    btn.showOff()	// "I'm clickable"
    			// "I'm focusable" 출력
}
```

# 클래스
## 클래스 변경자: 기본적으로 final
final 클래스는 상속이 불가능하다. 자바는 기본적으로 open 클래스지만 코틀린은 final 클래스이기 때문에 open을 명시해야만 상속이 가능하다.

```
class FinalClass{ ... }
open class OpenClass { ... }

class Child1 : FinalClass	// 컴파일 에러
class Child2 : OpenClass	// 상속 가능
```

이와 더불어 클래스의 메소드를 오버라이딩하려면 open 변경자를 명시해야만 자식 클래스에서 오버라이딩이 가능하다.

``` kotlin
open class Button : Clickable {
    fun disable() { ... }		// 오버라이드 불가
    open fun animate { ... }		// 오버라이드 가능
    override fun click1() { ... }	// 오버라이드한 메소드는 기본적으로 open
    final override fun click2() { ... }	// 오버라이드한 메소드의 상속을 금지하려면 final로 지정하면 된다.
}

```

## 가시성 변경자(접근 제어자)
자바에서는 가시성 변경자로 private, protected, defualt, public을 사용하고 기본적으로 defualt이다. 코틀린에는 default가 존재하지 않고 대신 internal이 추가되었다. 기본 가시성 변경자는 public이다.

자바의 default는 같은 패키지 내에서만 접근할 수 있었지만 internal은 같은 모듈 내에서만 접근이 가능하다. 여기서 모듈이란 같이 컴파일되는 모든 파일의 집합을 이야기한다. 예를들어 날짜 포맷을 반환하는 라이브러리를 만들었다고 할 때 이 라이브러리를 모듈로 볼 수 있다. 라이브러리를 사용하는 외부에서 internal 가시성 변경자로 선언된 클래스, 메소드는 사용할 수 없다.



| 변경자 | 클래스 멤버 | 최상위 선언 |
|-------|-----------|------------|
| public(기본) | 모든 곳에서 볼 수 있다 | 모든 곳에서 볼 수 있다 |
| internal | 같은 모듈 안에서만 볼 수 있다 | 같은 모듈 안에서 만 볼 수 있다 |
| protected | 하위 클래스 안에서만 볼 수 있다 | 최상위 선언 적용 불가 |
| private | 같은 클래스 안에서만 볼 수 있다 | 같은 파일 안에서만 볼 수 있다 |

> 자바에서 protected는 같은 패키지 내에서 접근이 가능했지만 코틀린에서는 그렇지 않다. 오직 자신이나 자손 클래스에서만 접근이 가능하다.

## 생성자
자바에는 기본 생성자가 존재하고 생성자를 오버로딩하여 사용한다. 코틀린은 이와 다르게 주 생성자와 부 생성자로 나뉜다.

### 주 생성자
자바에서 생성자를 중괄호({ }) 안에서 선언한다. 하지만 코틀린은 주 생성자로 클래스 중괄호 밖에서 선언한다. 코틀린으 클래스 정의를 한번 살펴보자.
``` kotlin
class User(val nickname: String)
```
이 함축된 선언을 풀어보면 다음과 같다.
``` kotlin
class User constructor(_nickname: String) {
    val nickname: String
    
    init {
        nickname = _nickname
    }
    
    or
    
    val nickname = _nickname
}
```

constructor 키워드를 이용해 생성자를 선언하고 init 초기화 블럭 안에서 인스턴스 초기화시 필요한 작업이 수행된다. 주 생성자에 가시성 변경자나 어노테이션의 적용이 필요한 경우가 아니라면 생략할 수 있다.

함수와 마찬가지로 생성자에도 기본 값을 설정할 수 있다. 모든 멤버변수에 기본 값이 존재하거나 주 생성자를 정의하지 않으면 파라미터가 없는 기본 생성자를 자동으로 생성해준다.

``` kotlin
class User(val nickname: String = "tkppp")

val user = User()
```

### 부모 클래스의 초기화
자바에서는 상속을 받는 자식 클래스에서 부모의 생성자를 super로 호출해 초기화해야 하는 경우가 있다. 코틀린에서는 이를 상속과 동시에 해결한다.

``` kotlin
open class User(val nickname: String = "tkppp") { ... }

// 상속과 동시에 부모 생성자 호출
class TwitterUser(nickname: String) : User(nickname) { ... }
or 
// 멤버의 기본값이 설정되어 있으므로 파라미터 생략 가능
class TwitterUser(nickname: String) : User() { ... }

```

코틀린은 구현과 상속에 있어 자바와 다르게 동일한 콜론(:)으로 표기한다. 상속에 있어 부모 초기화의 초기화는 필수이기에 클래스의 상속은 생성자를 호출하게 되므로 인터페이스 구현과는 구별된다.

### 부 생성자
자바와 다르게 코틀린의 요소 덕분에 부 생성자가 필요한 경우가 많지는 않지만 어쨌든 주 생성자 외에 다른 생성자가 필요한 상황이 생긴다. 가능한 경우 멤버변수의 기본값을 설정하여 주 생성자만 사용하는게 좋지만 어쩔 수 없는 상황에서 주 생성자 없이 부 생성자를 만들어야 한다.
``` kotlin
open class View {
    constructor(ctx: Context) { ... }
    constructor(ctx: Context, attr: Atrribute) { ... }
}
```

상속을 받는 클래스라면 주 생성자와 마찬가지로 부모 클래스의 초기화를 해주어야한다. 이때는 자바와 같이 super를 사용한다.
``` kotlin
class MyButton{
    constructor(ctx: Context) : this(ctx, MY_STYLE) { ... }
    constructor(ctx: Context, attr: AttributeSet): super(ctx, attr) { ... }
}

```
자바와 생성자 선언이 매우 유사하다.

## 추상 프로퍼티 구현
인터페이스에 자바와 다르게 추상 프로퍼티가 들어가므로 클래스에서 구현을 해주어야 한다. 메소드 오버라이드와 크게 다를건 없다. 방식이 여러개일 뿐이다.
``` kotlin
interface User {
    val nickname: String
}

// 주 생성자에서 구현
class PrivateUser(override val nickname: String) : User

// 필드와 커스텀 게터를 이용한 구현
class SubscribeUser(val email: String) : User {
    override val nickname
        get() = email.substringBefore('@')
}

// 필드에 구현
class FacebookUser(val accountId: Int) : User {
    override val nickname = getFacebookName(accountId)
}

fun main(){
    val user = SubscribeUser("gowldla0423@naver.com")
    println(user.nickname)
}
```

>  **예제의 커스텀 게터와 필드 구현의 차이**
>
> 커스텀 게터를 사용할 경우 뒷받침하는 필드에 값을 저장하지 않고 게터를 호출할 때마다 결과를 매번 계산하여 반환한다. 필드에 구현할 경우 뒷받침하는 필드에 값을 저장해 호출 시마다 불러온다.


## 커스텀 게터와 세터에서 뒷받침하는 필드에 접근하기
커스텀 게터와 세터를 구현할 때 field를 사용할 경우 코틀린은 뒷받침하는 필드를 생성해준다. 세터로 뒷받침하는 필드에 값을 저장하고 게터에서 뒷받침하는 필드에서 값을 꺼내와 넘겨주게 된다.
``` kotlin
class User(val name: String) {
    val address: String = "unspecified"
    	set(value: String){
            println("""
                Address was changed for $name:
                "$field" -> "$value".""".trimIndent())
        field = value
}

```

# 데이터 클래스
데이터 클래스란 프로퍼티만 가진 클래스로 모든 클래스가 정의해야 하는 메소드를 자동 생성해주는 코틀린의 특별한 클래스이다. 클래스 정의 시 class 앞에 data만 추가해주면 된다.

## toString(), equals(), hashCode()
hashCode()를 오버라이드 하지 않는다면 equals()를 오버라이드 했더라도 틀린 경우가 나타날 수 있다.
``` kotlin
val _hashSet = hashSetOf(Client("tkppp", 25))

println(_hashSet.contains(Client("tkppp", 25)))  // false 출력
```
분명 동일한 값을 가지는 false라는 결과를 얻었다. 이는 contains 메소드가 객체의 hashCode() 메소드를 사용하여 이용하여 포함됐는지 여부를 결정하기 때문이다. 이를 해결하려면 hashCode() 메소드를 오버라이드 해야한다.

``` kotlin
class Client(val name: String, val age: Int){
	override fun hashCode(): Int = name.hashCode * 31 + age
}

val _hashSet = hashSetOf(Client("tkppp", 25))

println(_hashSet.contains(Client("tkppp", 25)))  // true 출력
```

이러한 불편함을 해결하기 위해 데이터 클래스에서는 각 메소드를 적절하게 자동으로 생성해주어 해결했다.

``` kotlin
data class Client(val name: String, val age: Int)

val _hashSet = hashSetOf(Client("tkppp", 25))
println(_hashSet.contains(Client("tkppp", 25)))  // true 출력
println(Client("tkppp", 25))	// Client(name = "tkppp", age = 25) 출력
```

## copy() 메소드
데이터 클래스를 정의할 때 var로 프로퍼티를 만들어도 되지만 가급적 데이터 클래스는 불변 클래스로 만드는 것을 권장한다. 대신 copy() 메소드를 통해 일부 프로퍼티만 변경한 새 객체를 만들 수 있다.
``` kotlin
data class Client(val name: String, val age: Int)

val c1 = Client("tkppp", 25)
val c2 = c2.copy(age = 26)
println(c2)	// Client(name = "tkppp", age = 26) 출력
```

## 클래스 위임 - by 키워드

잘 모르겠다.... 나중에 다시 알아보자


# object 키워드 - 클래스 선언과 인스턴스 생성
object 키워드를 이용해 클래스를 정의함과 동시에 인스턴스 생성을 할 수 있다. object 키워드를 통해 할 수 있는 상황으로 주로 3가지를 들 수 있다.

## 객체 선언 - 싱글톤 구현
클래스의 인스턴스가 단 한개만 존재하는 클래스를 자바에서는 private 생성자를 통해 싱글톤 패턴으로 구현하였다. 하지만 코틀린은 object 키워드를 통해 싱글톤 패턴을 쉽게 구현할 수 있다.

``` kotlin
object Payroll {
    val allEmployees = arrayListOf<Person> ()
    fun calculateSalary(){
 		...   
    }
}

Payroll.calculateSalary()
```
객체 선언을 통해 클래스와 마찬가지로 프로퍼티, 메소드, 초기화 블록등이 들억갈 수 있다. 하지만 생성자(주,부 생성자)는 객체선언에 쓸 수 없다. 객체 선언과 동시에 인스턴스가 생성되므로 생성자가 필요없기 때문이다. 자바에서 정적(static) 필드를 사용하는 것처럼 객체의 메소드와 프로퍼티에 접근할 수 있다. 일반 클래스와 마찬가지로 클래스의 상속이나 인터페이스의 구현이 가능하다.

## 객체 선언 - 중첩 객체
클래스 안에서도 객체 선언할 수 있다. 클래스 안에 있다고 해서 객체가 각 인스턴스마다 존재하는 것이 아니다. 자바의 정적 필드와 동일하다고 보면 된다.
``` kotlin
data class Person(val name: String){
    object NameComparator : Comparator<Person> {
    	override fun compare(p1: Person, p2: Person): Int = p1.name.compare(p2)
    }
}
```
> 코틀린의 중첩 객체는 자바에서 INSTANCE 정적 멤버로 접근할 수 있다.

## 동반 객체
코틀린은 자바와 달리 static 키워드를 가지지 않는다. 이를 최상위 함수, 프로퍼티와 객체 선언을 활용한다. 하지만 최상위 함수는 클래스 내의 private 멤버에는 접근할 수 없는 문제가 있다. 이러한 경우 동반 객체를 사용하면 정적이면서 private 멤버에 접근할 수 있다.

동반 객체를 생성하기 위하서는 객체 선언에 **companion** 키워드를 앞에 붙여주면 된다. 중첩 객체와 같이 클래스 이름으로 호출할 수 있다.

``` kotlin
class User private constructor(val nickname: String){
    companion object {
        fun newSubscribingUser(email: String) = User(email.substringBefore('@'))
        fun newFacebookUser(accountId: Int) = User(getFacebookUser(accountId))
    }
}
```
위의 코드를 보면 주 생성자를 private으로 선언하였다. 하지만 동반객체로 팩토리 메소드를 사용해 private 생성자를 호출하여 인스턴스를 생성한다.

### 이름있는 동반 객체
동반 객체도 이름을 가질 수 있다. 단지 사용할때 동반 객체의 이름으로 메소드를 호출하면 된다.

> 무명 동반 객체는 자바에서 Companion 정적 멤버로 접근할 수 있다. 이름이 있는 경우 일반 정적 멤버처럼 호출하면 된다.

### 인터페이스 구현
객체 선언과 마찬가지로 동반 객체도 클래스의 상속, 인터페이스의 구현이 가능하다.

### 동반 객체의 확장
동반 객체에도 확장 함수, 프로퍼티를 이용한 확장이 가능하다.

## 객체 식
자바에서는 클래스를 매개변수로 넘길때 무명 객체를 사용한다. 코틀린에서는 object 키워드를 이용해 무명 객체를 만든다. 자바와 달리 클래스와 인터페이스를 단 한번만 상속하거나 구현할 수 있지 않고 여러 인터페이스를 구현할 수 있다. 자바와 마찬가지로는 객체 식이 포함된 함수의 변수에도 접근할 수 있다는 것이다.

>  객체 식은 싱글톤이 아니다. 객체 식이 쓰일때마다 새로운 인스턴스가 생성된다.


