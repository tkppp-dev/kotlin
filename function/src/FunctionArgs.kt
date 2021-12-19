/**
 * 코틀린은 함수의 파라미터에 기본값을 지정할 수 있다. like js
 * 만약 기존 함수(메소드)의 파라미터를 수정하고 싶다면 자바는 오버로딩을 할 것이다. 기존 함수(메소드)를 변경하면 기존에 해당
 * 함수(메소드)를 사용하는 코드에서는 모두 오류가 뜨기 때문이다.
 * 하지만 코틀린은 기본아규먼트를 통해 문제를 해결할 수 있다. (예제 1)
 *
 * 기본 아규먼트 사용에서 유의할 점
 * 기본 아규먼트를 사용한다고 해서 모든 경우에 생략이 가능한 것은 아니다.
 * 만약 기본 아규먼트 뒤에 일반 아규먼트가 존재한다면 기본 야규먼트의 값을 항상 지정해야한다. 즉 순서가 중요하다는 것이다.
 * 단, 명시적 아규먼트를 사용할 경우 순서는 중요치 않고 생략이 가능하다
 * 또, 람다 표현식이 인자로 사용되는 경우 람다 표현식 앞에 위치하면 된다.
 *
 * 명시적 아규먼트를 사용해서 함수 호출시 파라미터의 이름을 명시하고 값을 넘겨줄 수 있다. (예제 2)
 *
 * 다중인자 varags 키워드로 사용한다. varags 키워드로 특정 타입의 배열이 들어갈 수 있음을 알려주는 것이다.
 * 다중인자는 파라미터중 가장 뒤에 선언하는 것이 바람직하다. 만약 다중인자 뒤에 다른 파라미터가 있다면 그 파라미터는 반드시
 * 명시적 아규먼트로 작성해줘야 하기 때문에 불편하다.
 *
 * 다중인자 사용시 배열을 파라미터로 넘기면 타입이 다르기 때문에 컴파일 에러가 발생한다.
 * js의 펼침 연산자와 비슷한 스프레드 연산자 * 를 통해 분해할 수 있다. 제네릭인 리스트 컬렉션을 사용할 경우 타입 배열로 변환한 후
 * 사용해야 한다.
 *
 * 코틀린도 구조분해할당을 지원한다. 튜플(페어, 트리플)이나 데이터 클래스의 경우 구조분해 할당을 통해 편리하게 변수를 할당할 수 있다.
 * 굳이 할당하고 싶지 않은 부분이 있다면 _(언더스코어)를 통해 할당하지 않을 수 있다. 뒤의 내용을 생략하려면 그냥 아무것도 적지 않으면
 * 된다.
 * */

fun main() {
    println(greetFirst("Hola", "Kim"))
    println(greetMessage())

    printPersonInfo()

    val intArr = intArrayOf(1, 4, 53, 2)
    println(getMaxNumber(1, 2, 3, 4))
    println(getMaxNumber(*intArr))
}

// 예제 1 - Hello를 다른 인삿말로 바꾸고 싶을 떄
fun greet(name: String) = "Hello, $name"

fun greet(name: String, msg: String) = "$msg, $name" // java style overloading. 기존 함수는 그대로 냅둬야 함
fun greetKt(name: String, msg: String = "Hello") = "$msg, $name" // kotlin style. 기본값으로 Hello를 가지도록 기존 함수를 변경
fun greetFirst(msg: String = "Hello", name: String) = "$msg, $name" // 기본 아규먼트
fun greetMessage(msg: String = "Hello", name: String = "Park") = "$msg, $name" // 기본 아규먼트의 순서

// 예제 2 - 가독성을 위한 명시적 아규먼트
fun createPerson(name: String, age: Int, height: Int, weight: Int) {
    println("$name $age $height $weight")
}

fun printPersonInfo() {
    createPerson("Park", age = 25, height = 175, weight = 88)  // 명시적 아규먼트의 사용. 선택적으로 사용하면 된다.
}

// 예제 3 - 다중 인자
fun getMaxNumber(vararg numbers: Int): Int {
    var max = Int.MIN_VALUE
    for (number in numbers) {
        max = if (max < number) number else max
    }
    return max
}

// 예제 4 - 구조분해 할당
fun desructuring() = Triple("John", "Quincy", "Adams")
fun declareValue(){
    val (first, _, last) = desructuring()
}