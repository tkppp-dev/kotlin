포스팅 주소 : https://velog.io/@tkppp-dev/%EC%96%B4%EB%85%B8%ED%85%8C%EC%9D%B4%EC%85%98

# 어노테이션이란?
프로그램의 소스코드 안에 다른 프로그램을 위한 정보를 미리 약속된 형식으로 포함시킨 것을 어노테이션(Annotation)이라고 한다.

예를 들어, 자신이 작성한 코드 중 특정 메소드만 테스트하기를 원한다면 @Test 어노테이션으로 테스트 프로그램에게 해당 메소드를 테스트하기 윈한다고 알릴 수 있다. 테스트 프로그램은 모든 메소드를 테스트하는게 아닌 @Test 어노테이션이 붙은 메소드만 테스트하면 되는 것이다.

## 표준 어노테이션

| 어노테이션 | 설명 |
| -------- | -----|
| @Override | 컴파일러에게 오버라이딩된 메소드라는 것을 알린다 |
| @Deprecate | 해당 메소드가 Deprecate 되었다는 것을 알린다 |
| @SuppressWarnings | 컴파일러에게 특정 경고 메세지가 나타나지 않게 해준다 |
| @SafeVarags | 제네릭 타입의 가변인자에 사용한다 |
| @FunctionalInterface | 함수형 인터페이스라는 것을 알린다 |
| @Native | native 메소드에 참조되는 상수를 앞에 붙인다 |


### @SuppressWarnings
SuppressWarnings 어노테이션은 컴파일러에게 어노테이션의 파라미터로 넘긴 종류의 경고 메세지를 나타내지 않게하는 어노테이션이다.

| 파라미터 | 설명 |
| ------- | ---- |
| deprecation | deprecate 관련 경고 메세지 차단 |
| unchecked | 제네릭으로 타입을 지정하지 않았을 때 발생하는 경고 차단 |
| rawtypes | 제네릭을 사용하지 않았을 때 발생하는 경고 차단 |
| varargs | 가변인자로 제네릭 타입을 사용했을때 발생하는 경고 차단 |


## 메타 어노테이션
메타 어노테이션은 어노테이션을 정의할 때 사용하는 어노테이션으로 어노테이션의 적용 대상이나 유지기간등을 설정한다.

### @Target
어노테이션이 적용되는 범위를 지정하는 어노테이션이다. 사용되는 매개변수는 다음과 같다.

| 대상 타입 | 설명 |
| ------- | ---- |
| ANNOTATION_TYPE | 어노테이션에 사용 가능 |
| CONSTRUCTOR | 생성자에 사용 가능 |
| FIELD | 멤버변수, ENUM 인스턴스와 같은 필드에 사용가능 |
| LOCAL_VARIABLE | 지역 변수에 사용 가능 |
| METHOD | 메소드에 사용 가능 |
| PACKAGE | 패키지에 사용 가능 |
| PARAMETER | 매개변수에 사용 가능 |
| TYPE | 클래스, 인터페이스, ENUM과 같은 타입 정의에 사용 가능 |
| TYPE_PARAMETER | 타입 매개변수(제네릭)에 사용 가능 |
| TYPE_USE | 클래스, 인터페이스와 같은 타입의 인스턴스를 할당할 때 사용 가능 |

### @Retention
어노테이션의 유지 기간을 지정하는데 사용하는 어노테이션이다 사용되는 매개변수는 다음과 같다.

| 유지 정책 | 설명 |
| ------- | ---- |
| SOURCE | 소스파일에만 존재하면 컴파일타입에만 접근 가능하다 |
| CLASS | 클래스파일에 존재하지만 실행시에는 사용 불가하며 기본값이다 |
| RUNTIME | 클래스파일에 존재하면 실행시 사용 가능 |

### @Documented
어노테이션에 대한 정보가 javadoc으로 작성한 문서에 포합되도록 하는 어노테이션

### @Inherited
어노테이션이 자손 클래스에도 적용되게 하는 어노테이션이다. Inherited 어노테이션이 적용된 어노테이션은 조상에 적용시 자식 클래스에도 적용된다.

### @Repeatable
어노테이션이 한 곳에 여러번 사용될 수 있도록 하는 어노테이션이다.


## 커스텀 어노테이션

### 커스텀 어노테이션 정의
커스텀 어노테이션을 정의하는 방법은 interface를 이용한다. interface 키워드 앞에 @만 붙여주면 된다.

``` java
public @interface AnnotationName {
	타입 요소 이름();
}
```

### 어노테이션 요소
어노테이션 내에 선언된 메소드를 어노테이션 요소라고 하며 어노테이션을 적용할 때 넘겨주는 파라미터이다.

``` java
@Target(TYPE)
public @interface TestInfo{
    int count();
    String testedBy(); 
    String[] testTools();
    TestType testType();
    DateTime testDate();
}

public @interface DateTime(){
	String yymmdd();
    String hhmmss();
}

@TestInfo(count = 3, testedBy = "Park", 
	  testTools = {"JUnit, AutoTester"}, testType = TestType.FIRST,
	  testDate = @DateTime(yymmdd = "211211", hhmmss="235959")
)
public class MyClass { ... }
```

어노테이션을 적용할 때 어노테이션 요소의 이름을 명시하기 때문에 순서는 관계없다. 어노테이션 요소는 기본값을 가질 수 있으며 default 키워드로 값으로 지정한다. 만약 모든 요소의 기본값이 설정되어 있다면 파라미터를 생략할 수 있다.
``` java
public @interface TestInfo{
    int count() default 1;
}

@TestInfo
public class MyClass { ... }
```

어노테이션 요소가 오직 하나이며 요소의 이름이 value인 경우 어노테이션을 적용할 때 요소의 이름을 생략하고 값만 적을 수 있다.
``` java
public @interface TestInfo{
    int value();
}

@TestInfo(1)
public class MyClass { ... }
```

배열의 어노테이션 요소를 적용할 때는 { }와 콤마를 이용해 배열을 표기한다.
``` java
public @interface TestInfo{
	String[] info() default {"aaa", "bbb"};
}

@TestInfo(info = { })	// 빈 값을 넘기기 위해서는 빈 { }를 명시해야 한다
@TestInfo(info = "ccc")	// 배열의 길이가 한개일 경우 중괄호를 생략할 수 있다.
@TestInfo(info = {"ccc", "ddd"})	
```

> **어노테이션 요소의 규칙**
> - 어노테이션 요소의 타입은 기본형, String, Enum, Class, Annotation만 허용된다.
> - ( ) 안에 매개변수를 선언할 수 없다.
> - 예외를 선언할 수 없다.
> - 요소를 타입 매개변수(제네릭)으로 정의할 수 없다.

## 마커 어노테이션
어노테이션 요소를 하나도 정의하지 않은 빈 어노테이션을 마커 어노테이션이라고 한다.


## 어노테이션을 어떻게 사용할까?
어노테이션은 다른 프로그램을 위한 정보를 미리 약속된 형식으로 포함시킨 것이라고 위에서 보았는데, 내부적으로 어노테이션을 어떻게 사용하는지 알아보자.

``` java
@SuppressWarnings("1111") // 유효하지 않은 어노테이션은 무시된다
@TestInfo(testedBy = "tkppp")
public class Main {
   public static void main(String[] args){
       Class<Main> cls = Main.class;
       TestInfo anno = (TestInfo) cls.getAnnotation(TestInfo.class);

       System.out.println("testCount : " + anno.count());
       System.out.println("testedBy : " + anno.testedBy());
       System.out.println("testTool : " + anno.testTools());
   }
}

@Retention(RetentionPolicy.RUNTIME)
public @interface TestInfo {
    int count() default 1;
    String testedBy();
    String testTools() default "JUnit";
}
```

자바리플렉션(?)을 통해 클래스 객체를 얻고 클래스 객체의 getAnnotation 메소드를 이용하여 어노테이션 객체를 얻을 수 있다. 이 정보들을 이용해 내부 로직을 만드는 것이다. 자세한 내용은 리플렉션을 배워야 알 수 있을것 같다.
