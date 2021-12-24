포스팅 주소 : https://velog.io/@tkppp-dev/%EB%9E%8C%EB%8B%A4

# 람다 식
자바는 함수가 일급객체가 아니기 때문에 파라미터로 함수를 넘길 수 없다. 람다는 함수형 프로그래밍의 함수처럼 코드 블럭을 메소드와 함수의 파라미터로 넘길 수 있다.

``` java
button.setOnClickListener(new OnClickListener() {
    @Override
    public void onClick(View view) { ... }
});
```
람다 이전에는 로직을 파라미터로 넘기기 위해 무명 클래스를 이용했다. 하지만 람다를 이용하면 번잡한 코드들이 필요하지 않다.

``` kotlin
button.setOnClickLisener { ... }
```

## 람다 식 문법
람다는 값처럼 여기저기 전달할 수 있는 동작의 모음이다. 람다를 변수에 저장할 수는 있지만 대부분 함수의 인자로 넘기면서 람다를 사용한다. 일반 문법은 자바스크립트의 화살표 함수와 유사하다.

``` js
// 화살표 함수
(x, y) => x + y
```

``` kotlin
// 람다 - 파라미터에 괄호가 필요하지는 않다.
//    파라미터      본문(로직)
{ x: Int, y: Int -> x+y }
```

원한다면 자바스크립트의 즉시실행함수처럼 정의와 동시에 실행할 수 있다. 혹은 run 키워드를 이용해서 가독성 좋게 즉시 실행할 수 있다.

``` kotlin
{ println(423) } ()
run { println(423) }
```

### 람다 식 문법 심화
``` kotlin
val people = listOf(Person("Alice", 29), Person("Bob", 27))

println(people.maxBy{ it.age })
println(people.maxBy({ p: Person -> p.age }))
```
위의 코드에서 maxBy 메소드에 넘긴 람다식은 서로 같다. 람다식이 파라미터의 마지막 인자면 ( ) 괄호밖으로 뺄 수 있다.
``` kotlin
println(people.maxBy(){ p: Person -> p.age })
```
여기서 람다가 함수의 유일한 인자라면 () 괄호를 생략할 수 있다.
``` kotlin
println(people.maxBy{ p: Person -> p.age })
```
식이 매우 간단해졌다. 여기서 더 식을 간소화 할 수 있다. maxBy 인스턴스는 호출한 people은 List<Person\>의 인스턴스이므로 넘겨지는 p의 타입이 Person임을 컴파일러가 추론할 수 있으므로 생략 가능하다.
``` kotlin
println(people.maxBy{ p -> p.age })
```
여기서 람다 식의 인자가 단 한개 뿐이고 파라미터 타입을 추론할 수 있다면 파라미터의 디폴트 이름 **it**으로 파라미터에 접근할 수 있다. it은 파라미터를 지정하지 않으면 자동으로 생성해주는 이름이다. 따라서 최종적인 람다식은 아래와 같다.
``` kotlin
println(people.maxBy{ it.age })
```

### 본문이 여러줄인 람다
본문이 여러줄인 람다식은 함수 본문처럼 { } 중괄호로 감싸지 않는다. 또한 if, when 같이 본문의 마지막 줄의 식이 리턴값이 된다.

``` kotlin
val sum = { x: Int, y: Int ->
    println("Compute Sum...")
    x + y
}
```

### 주의점
인자가 아닌 변수에 람다 식을 작성할 경우 타입 추론이 작동할 문맥이 없으므로 항상 파라미터의 타입을 명시해주어야 한다.


## 변수 포획
람다를 함수 안에서 정의하면 함수의 파라미터 뿐만 아니라 람다 정의에 앞에 선언된 로컬 변수까지 람다에서 사용할 수 있다. 자바도 마찬가진데 자바에서는 final 변수에만 접근할 수 있었다. 코틀린에서는 이와 다르게 var로 선언된 mutable 변수에도 접근하고 조작할 수 있는 차이점이 있다.

``` kotlin
fun printProblemCounts(response: Collection<String>) {
    var clientErrors = 0;
    var serverErrors = 0;
    response.forEach {
        if (it.startsWith("4")) {
            clientErrors++
        } else if (it.startsWith("5")) {
            serverErrors++
        }
    }

    println("$clientErrors clent errors, $serverErrors server errors")
}

fun main(){
    val response = listOf("200 Ok", "404 Not found", "500 Internal Server Error")
    printProblemCounts(response)
}
```

### 주의점
자바스크립트의 비동기 함수와 비슷한 문제점을 가진다. 비동기적으로 실행되는 코드에 람다가 사용될 경우 함수 호출이 끝난 후에 로컬 변수가 변경될 수도 있다.
``` kotlin
fun tryToCountButtonClicks(button: Button): Int{
    var clicks = 0
    button.onClick{ clicks++ }	// 비동기 함수 onClick
    return clicks
}

println(tryToCountButtonClicks(button))	// 0 출력
```

## 멤버 참조
만약 이미 선언된 함수를 파라미터로 넘기고 싶다면 어떻게 할까? 람다를 배웠으니 함수를 실행시키는 람다식을 작성하여 넘기면 된다. 하지만 더 간단하게 할 수 있는 방법이 있다. 바로 멤버 참조를 이용하는 것이다.

::(이중 콜론)을 이용해 멤버 참조식을 만들 수 있다. 멤버 참조는 프로퍼티나 메소드를 단 하나만 호출하는 함수 값을 만들어 준다.

### 프로퍼티 참조
> ClassName::(propery or method)Name 형식으로 멤버 참조를 선언한다.

``` kotlin
class Person(val name: String, val age: Int){
    fun foo() = println("bar")
}

people.maxBy(Person::age)
people.maxBy{ it.age }
people.maxBy{ p: Person -> p.age }
```

위의 코드에서 멤버참조를 이용한 것과 람다식은 모두 동일하다.

### 메소드 참조

``` kotlin
val foo = Person::foo
foo(Person("tkppp", 25))	// "bar" 출력
run({ p: Person -> p.foo(25) })

val p = Person("typpp", 15)
val foo2 = p::foo
foo2()
```
메소드를 멤버 참조하는 경우에는 메소드의 첫번째 인자로 해당 메소드를 실행한 인스턴스를 넣어주어야 한다. 즉 메소드의 인자가 없더라도 메소드를 가진 클래스 인스턴스를 지정해야된다는 것이다.

혹은 생성한 인스턴스의 메소드를 참조할 수 있다. 이 경우 첫번째 인자로 인스턴스를 넣어주지 않아도 된다.

확장함수도 멤버 함수와 동일하게 참조할 수 있다.


### 최상위 함수, 프로퍼티 참조
최상위에 선언된 다른 클래스의 멤버가 아닌 함수나 프로퍼티도 참조할 수 있다. 이때는 클래스를 생략하면 된다.

``` kotlin
fun foo = println("bar")
run(::foo())	// "bar" 출력
```

### 생성자 참조
생성자 참조를 이용해 인스턴스의 생성을 연기할 수 있다. 생성자를 외부 변수에 담아둔다고 생각하면 된다. 생성자 참조는 :: 뒤에 클래스이름으로 선언한다.
``` kotlin
val createPerson = ::Person
val p = createPerson("tkppp", 25)
```

# 컬렉션 함수형 API
## filter 함수
filter 함수는 컬렉션을 순회하면서 true를 반환하는 원소만 새로 담은 컬렉션을 반환하는 함수다.

``` kotlin
data class Person(val name: String, val age: Int)
val people = listOf(Person("tkppp", 25), Person("typpp", 15))
println(people.filter{ it.age > 20 })
```
filter 함수는 기존 원소의 값을 변경할 수는 없다. 기존 원소 값을 조작한 결과 컬렉션을 얻고 싶다면 map 함수를 사용한다.

## map 함수
map 함수는 컬렉션을 순회하면서 반환하는 값을 모은 새로운 컬렉션을 반환하는 함수다.

``` kotlin
data class Person(val name: String, val age: Int)
val people = listOf(Person("tkppp", 25), Person("typpp", 15))
println(people.map{ it.name })
or
println(people.map(Person::name))
```

## all, any 함수
컬렉션의 원소가 어떠한 조건을 만족하는지 여부를 알기 위해서는 all, any 함수를 이용한다.
> - all( ) : 모든 원소가 조건을 만족하는지 검사
> - any( ) : 원소중 하나라도 조건을 만족하는지 검사

``` kotlin
people.all{ it.age > 20 }	// false 반환
people.any { it.age > 20 }	// true 반환
```

## count 함수
컬렉션의 원소 중 조건을 만족하는 원소의 수를 반환하는 함수다.
``` kotlin
people.count{ it.age > 20 }	// 1 반환
```

## find 함수
컬렉션의 원소 중 조건을 만족하는 원소 하나를 반환하는 함수다. 만약 조건을 만족하는 원소가 있다면 조건을 만족하는 첫번째 원소를 반환하고 없다면 null을 반환함에 유의해야한다.

```
people.find{ it.age > 20 } // Person("tkppp", 25) 반환
people.find{ it.age > 30 } // null 반환
```

## groupBy 함수
컬렉션의 원소를 어떤 특성에 따라 여러 그룹으로 나누고 싶을 때 groupBy 함수를 사용한다. groupBy 함수는 기준이 되는 특성으로 컬렉션을 Map으로 구분하여 반환한다.

``` kotlin
val people = listOf(Person("Kim", 25), Person("Park", 25), Person("Lee", 26))

println(people.groupBy{ it.age }) // {25 = [Person("Kim", 25), Person("Park", 25)], 26 = [Person("Lee", 26)]} 출력
```

위의 예제를 보면 클래스의 나이를 기준으로 리스트를 Map<Int, List<Person\>으로 구분했다.

## flatMap, flatten 함수
flatMap 함수는 컬렉션을 순회하면서 각 원소에 람다를 적용(map)하고 거기서 얻어지는 여러 리스트를 한 리스트로 모은다.
``` kotlin
val strings = listOf("abc", "def")
println(strings.flatMap { it.toList() }) // [a,b,c,d,e,f] 출력
```
String.toList() 메소드는 문자열을 분해하여 문자 리스트를 반환하는 메소드다. toList() 적용시 "abc".toList() == [a,b,c] 이다. flatMap 함수는 toList()로 얻어지는 두 리스트([a,b,c], [d,e,f])를 병합하여 한 리스트로 만든다.

flatten 함수는 리스트 안의 리스트를 하나의 리스트로 만들어 반환한다. 무슨 소린지 잘 이해가 안가니 코드를 보자

``` kotlin
// [["abc", "def"], ["ABC", "DEF"]] - 리스트 안의 리스트
val lists = listOf(listOf("abc", "def"), listOf("ABC", "DEF"))

println(lists.map { list -> list.flatMap { it.toList() } }.flatten()) // [a, b, c, d, e, f, A, B, C, D, E, F] 출력
```

list.flatMap { it.toList( ) } 를 통해 내부 리스트 각각 [ a, b, c, d, e, f ], [ A, B, C, D, E, F ]로 변환하였다. 이때 내부의 리스트를 하나로 합친 리스트로 만드는것이 flatten 함수다.

> 결과적으로 flatten과 flatMap이나 내부의 리스트를 하나의 리스트로 합친다는 것은

## 지연 계산 컬렉션
map과 filter를 연쇄해서 사용한다고 가정해보자.
``` kotlin
people.map(Person::name).filter { it.startsWith("A") }
```
이 경우에는 map 연산이 완료된 후 반환된 이터레이터 객체를 filter 연산하여 결과물을 반환한다. 이터레이터 객체가 크기가 작다면 이 연쇄는 문제되지 않는다. 하지만 이터레이터 객체의 크기가 매우 크다면 성능 문제가 발생할 수 있다. 따라서 각 연산을 컬렉션 객체를 이용한는 것이 아니라 시퀀스 객체로 변환하여 하도록 해야한다.

시퀀스 객체는 자바에서 스트림과 유사한 개념이다. 하지만 컬렉션 객체와 같이 자바 스트림을 그대로 사용하지는 않는다. 시퀀스는 스트림과 개념적으로만 같고  코틀린에서 새롭게 정의된 인터페이스다.

시퀀스를 선언은 sequenceOf 함수 또는 이터러블 인스턴스에서 asSequence 메소드를 이용하면 된다.

``` kotlin
val seq1 = sequenceOf(1,2,3,4)
val seq2 = listOf(1,2,3,4).asSequence()
```

시퀀스 연산은 컬렉션의 연쇄 연산과 다르게 값을 저장하는 중간 컬렉션이 생기지 않고 순차적으로 처리된다. map과 filter과 연쇄적으로 일어나는 상황을 가정하면, 컬렉션의 연산은 전체 컬렉션의 map을 완료한 후 filter를 수행한다. 하지만 시퀀스는 시퀀스의 첫번째 원소의 map을 완료한후 filter를 수행한다. 그 다음으로는 두번째 원소의 map을 완료하고 filter를 수행한다. 이를 마지막 원소까지 반복한다.

### 시퀀스 연산 실행 - 중간 연산과 최종 연산
시퀀스에 대한 연산은 중간 연산과 최종 연산으로 나뉜다. 중간 연산은 다른 시퀀스를 반환한다. 이 시퀀스는 이전 단계 원소의 변환 방법을 알고 있다. 이것이 중첩되면 순차적으로 원소를 변환하는 시퀀스 객체가 생성된다.

``` kotlin
// 중간 연산
listOf(1,2,3,4).asSequnce().map{ it*it }.filter { it % 2 == 0 }

// 시퀀스 연산 로직
val result: MutableList<Int> = MutableListOf()
for(el in listOf(1,2,3,4)){
    val step1 = it * it				// step1
    if(step1 % 2 == 0 ) result.add(step1)	// step2
}
```
위의 코드에서 map과 filter가 바로 중간 연산이다. 공식 문서를 보고 이해한 바를 코드로 작성해봤다. 시퀀스의 연산의 연쇄로 위의 코드와 같은 연산을 수행하는 시퀀스가 만들어진다.

그렇다면 최종 연산은 무엇일까. 위의 코드만으로는 아무런 연산이 이루어지지 않는다. 단지 연산을 위한 시퀀스만 생성했을 뿐이다. 최종 연산이 최종적으로 이루어져야지만 연산이 이루어진다. 최종 연산은 결과를 반환하는 것으로 결과는 시퀀스로부터 일련의 계산을 수행해 얻을 수 있는 컬렉션, 원소, 숫자 또는 객체다.
``` kotlin
// 중간 연산 + 최종 연산
listOf(1,2,3,4).asSequnce().map{ it*it }.filter { it % 2 == 0 }.toList()
```
위와 코드에서 toList()를 통해 시퀀스를 리스트로 변환했다. 여기서 toList()가 최종 연산부이며 toList()로 결과를 반환하기 때문에 연산이 수행되고 결과가 반환된다.

### 시퀀스 연산의 중단
만약 연산을 시퀀스에 따라 수행하다가 중간에 결과가 얻어지면 그 이후의 원소에 대해서는 변환이 이루어지지 않을 수 있다.
``` kotlin
listOf(1,2,3,4).asSequnce().map{ it*it }.find{ it > 3 }
```
시퀀스에 따라 연산이 수행되면 map(1) = 1, find(1) = false -> map(2) = 4, find(4) = true로 두번째 원소의 연산에서 원하는 값을 도출했다. 이런 경우에는 남은 원소의 연산이 종료되며 결과를 반환한다.


# 자바 함수형 인터페이스 활용
자바에서는 구현할 메소드가 단 한개인 인터페이스를 함수형 인터페이스로 활용하고 무명 클래스의 인스턴스를 파라미터로 넘겨 함수형 프로그래밍을 구현하였다. 하지만 코틀린에서는 함수형 인터페이스를 인자로 원하는 자바 메소드에 코틀린 람다를 넘길 수 있다.

``` java
// 자바 API
public interface OnClickListener {
    void onClick(View view)
}

button.setOnClickListener(new OnClickListener() {
    @Override
    public void onClick(View view) { ... }
}
```

``` kotlin
// 코틀린에서 사용
button.setOnClickListener { view -> ... } 
```

> 코틀린은 함수가 일급 객체이다. 자바스크립트처럼 자바 메소드에 람다 대신 코틀린 함수를 넘기면 이는 작동하지 않는다.

## object 무명 객체와의 차이
object 키워드를 통해 자바의 무명 클래스와 동일한 작업을 할 수 있다. 람다와 무명 객체의 차이는 무명 객체는 매 호출시마다 새로운 인스턴스를 생성하고 람다는 하나의 인스턴스만을 사용한다는 것이다.

> 람다가 주변의 변수를 포획해서 사용하는 경우 하나의 인스턴스를 사용할 수 없다. 이 경우에는 새 인스턴스를 호출시마다 생성한다.

## SAM 생성자 - 람다를 함수형 인터페이스로 명시적으로 변경
함수형 인터페이스는 SAM(Single Abstract Method) 인터페이스라고도 불리는데 코틀린에서 람다를 컴파일러가 함수형 인터페이스의 무명 클래스로 변환하지 못하는 경우 SAM 생성자를 통해 람다를 함수형 인터페이스 객체로 변경할 수 있다.
``` kotlin
fun CreateAllDoneRunnable : Runnable {
    return Runnable { println("All done!") }
}
```
SAM 생성자는 SAM 인터페이스명 본문 { 람다 식 } 으로 사용하며 변수에 저장하는 것도 가능하다.

# 수신 객체 지정 람다

## with 함수
어떤 객체의 이름을 반복하지 않고도 그 객체에 대해 다양한 연산을 수행할 수 있다면 좋을 수 있을 것이다. with 함수를 통해 이를 달성 할 수 있다
``` kotlin
fun alphabet(): String {
    val result: StringBuilder = StringBuilder()
    for(letter in 'A'..'Z') {
        result.append(letter)
    }
    result.append("\nNow I know the alphabet!")
    return result
}
```
위의 예제를 보면 StringBuilder 객체르 담은 result 객체를 지속적으로 사용하는 것을 볼 수 있다. 물론 이정도가 나쁘진 않지만 with 함수를 통해 더 간단하게 만들 수 있다.

``` kotlin
fun alphabet() = with(StringBuilder()) {
   for(letter in 'A'..'Z') append(letter)
   append("\nNow I know the alphabet!")
   this.toString()
}
```
식이 매우 간단해졌다. with 함수는 확장함수의 수신 객체와 비슷하다. with 함수의 인자로는 전달할 수신 객체와 로직을 수행할 람다 식이다. 위의 예제에서 함수를 정의하는 것처럼 보이겠지만 사실은 with 함수의 두번째 인자로 람다식이 사용되었다. 확장 함수 내에서 수신 객체에 this를 생략하여 접근하는 것처럼 with 함수로 전달된 수신 객체를 람다식에서 사용할 수 있다.

## apply 함수
apply 함수는 with 함수와 거의 유사하지만 항상 수신 객체를 리턴한다. apply 함수는 확장 함수로 정의되어 있으므로 전달하려는 수신 객체의 메소드로 호출하면 된다.

``` kotlin
fun alphabet() = StringBuilder().apply {
   for(letter in 'A'..'Z') append(letter)
   append("\nNow I know the alphabet!")
}.toString
```