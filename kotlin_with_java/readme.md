포스팅 주소 : 

## 코틀린의 상호 운용성
코틀린은 기존의 자바 라이브러리와 100% 호환된다.
이말이 코틀린과 자바를 한 파일에 혼용해서 코드를 작성하는 것을 의미하는 것은 아니다.

처음 코틀린을 접했을 때 자바와 코틀린의 상호 운용성을 코틀린 파일에 자바 코드를 같이 쓸 수 있다는 건가? 하는 생각을 했지만 코틀린이 자바와 100% 호환된다는 말은 자바 클래스(객체)를 코틀린 식으로 인스턴스를 생성하고 사용할 수 있다는것을 의미한다.

``` java
package _java;

public class SampleJavaClass {
    public static int rectangleArea(int x, int y) {
        return x * y;
    }

    private String name = "Java";

    public String getName() {
        return name;
    }

    public void setName(String value) {
        name = value;
    }

    public String getRandom(int n){
        if(n%2 == 0){
            return name;
        }
        else{
            return null;
        }
    }
}
```

``` kotlin
package _kotlin

import _java.SampleJavaClass

fun main(){
    val rectangleArea: Int = SampleJavaClass.rectangleArea(2, 4)
    val javaSample: SampleJavaClass = SampleJavaClass()
    val randomName: String? = javaSample.getRandom(1)

    println("in the Kotlin codes : $rectangleArea")
    // 자바 클래스의 멤버에 접근할떄도 게터와 세터 필요 X. 내부적으로 호출
    javaSample.name = "Kotlin"
    println("java member variable getter : ${javaSample.name}")
    println(randomName)
}
```