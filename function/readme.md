포스팅 주소 : https://velog.io/@tkppp-dev/%ED%95%A8%EC%88%98-%EC%BD%94%ED%8B%80%EB%A6%B0

## 최상위 함수와 최상위 프로퍼티
### 최상위 함수
자바와는 다르게 코틀린에서는 메소드가 아닌 함수가 존재할 수 있다. 코틀린 컴파일러가 자동으로 최상위 메소드를 static 메소드로 변환해주기 때문이다.

``` kotlin
// Join.kt 파일
package strings

fun joinToString(...) { ... }
```

``` java
// 컴파일 된 코틀린 코드의 자바 버전
package strings

public class JoinKt{
    pulbic static String joinToString(...) { ... }
}
```

코틀린은 컴파일 시 FilenameKt의 이름으로 클래스를 생성한다.

### 최상위 프로퍼티
함수와 마찬가지로 최상위 프로퍼티를 선언할 수 있다. 하지만 val, var를 사용할 경우 자바로 변환시 private static final으로 변환된다. 코틀린에서는 내부적으로 게터와 세터를 호출하므로 문제가 없지만 자바로 변환 시에는 게터와 세터를 사용해야 한다. 이 문제는 **const val**을 사용하면 해결된다. const val 사용시 public static final으로 변환된다.

``` kotlin
// Join.kt 파일
package strings

val str1 = "privite static"
const val str2 = "public static"
```

``` java
// 컴파일 된 코틀린 코드의 자바 버전
package strings

public class JoinKt{
    public static final String str2= "public static"
    private static final String str1 = "private static"
}
```

## 확장 함수, 프로퍼티
기존 코드에 코틀린 코드를 자연스럽게 통합하는 것은 코틀린의 핵심 목표중 하나다. 기존에 존재하는 자바 라이브러리를 코틀린 프로젝트로 변환하려 했을 때 직접 변환이 불가능하거나 미처 변환하지 못한 자바 코드를 처리해야한다. 자바 API를 재작성하지 않고도 확장함수를 이용하여 코틀린스럽게 처리할 수 있다.

### 확장 함수란?
확장 함수는 어떤 클래스의 멤버 메소드인 것처럼 호출할 수 있지만 그 클래스의 밖에서 선언된 함수다.

``` kotlin
package strings

// Kotlin.String 클래스에 문자열의 마지막을 가져오는 메소드를 추가하는 확장 함수
fun String.lastChar() : char = this.get(this.length - 1)

fun useExtension(){
	println("Kotlin".lastChar()) // n 출력
}
```

확장 함수를 만드는 것은 간단하다. 함수를 추가하려는 클래스의 이름을 앞에 덧붙이면 된다. 이 때 덧붙이는 클래스를 **수신 객체 타입**이라 하고 확장 함수가 호출되는 대상이 되는 값(객체)를 **수신 객체**라고 한다. 위의 예제에서 수신 객체 타임은 **String 클래스**이고 수신 객체는 **Kotlin(this)**다.

> 확장 함수의 본문에서도 클래스 안에서 사용하는 것과 같이 this를 생략할 수 있다. 단, 클래스 내부의 보호된 멤버(private, protected)는 접근할 수 없음에 유의해야 한다.

### 확장 함수의 사용
확장 함수로 클래스의 메소드를 생성했다고 해도 자동으로 프로젝트의 모든 곳에서 확장 함수 메소드를 사용할 수 없다. import를 통해 해당 확장 함수 메소드를 불러와야 한다.
``` kotlin
import strings.lastChar
or
import strings.*

val c = "Kotlin".lastChar()
```
``` kotlin
// as를 이용한 이름 변경
import strings.lastChar as last

val c = "Kotlin".last()
```

### 자바에서의 확장 함수
자바에서 코틀린의 확장 함수는 수신 객체를 첫번째 인자로 받는 정적 메소드일 뿐이다. 실제로 자바 클래스는 영향을 받지 않는다.
``` java
// lastChar 확장함수가 StringUtil.kt에 있다면
char c = StringUtilKt.lastChar("Kotlin")
```

### 확장 함수의 오버라이딩
확장 함수의 오버라이딩은 불가능하다. 부모를 상속받은 자식이 메소드를 오버라이딩하면 업캐스팅이 되더라도 자식에서 오버라이딩된 메소드를 호출한다. 하지만 확장 함수는 오버라이딩이 가능하지 않기 때문에 아래와 같은 결과가 나타난다.
``` kotlin
open class View{ 
	open fun click() = println("I'm View")
}
class Button : View {
	override fun click() =  println("I'm Button")
}

fun View.showOff() = println("I'm View")
fun Button.showOff() = println("I'm Button")

fun cantOverride(){
    val view: View = Button()
    view.click()	// "I'm Button" 출력 
    view.showOff()	// "I'm View" 출력
}
```

확장 함수는 클래스의 일부가 아니고 정적으로 선언되기 때문에 오버라이드가 되지 않는다.

> 확장 함수와 클래스 메소드의 시그니처가 동일하다면 확장 함수가 아닌 클래스의 메소드를 호출한다. 우선 순위가 클래스 메소드가 먼저이기 때문이다.


### 확장 프로퍼티
확장 프로퍼티의 정의는 확장 함수와 동일하다. 수신 객체와 수신 객체 클래스가 추가되었을 뿐이다. 하지만 뒷받침하는 필드가 없기 때문에 커스텀 게터를 반드시 정의해야 한다.
``` kotlin
var String.lastChar: Char
    get() = get(length - 1)
    set(value: Char){
    	this.setCharAt(length - 1, value)
```

