포스팅 주소 : https://velog.io/@tkppp-dev/%EC%A0%9C%EB%84%A4%EB%A6%AD

## 제네릭(Generics)
제네릭은 다양한 타입의 객체들을 다루는 메서드나 컬렉션 클래스에 컴파일 시의 타입 체크를 해주는 기능이다. 객체의 타입을 컴파일 시에 체크하기 때문에 객체의 타입 안정성을 높이고 형변환의 번거로움이 줄어든다.

컬렉션 클래스와 같이 다양한 종류의 객체를 담는 클래스들은 Object 객체를 이용해 객체를 담았을 것이다. 하지만 컬렉션에서 객체를 꺼내 쓰려면 형변환을 해야 객체를 기능을 온전히 사용할 수 있다. 이러한 불편함을 제네릭을 통해 해결하는 것이다.

### 제네릭 클래스
``` java
// 다형성을 이용한 클래스
class Box{
    Object item;
    
    void setItem(Object item){ this.item = item; }
    Object getItem() { return item; }
}

// 제네릭 클래스
class Box<T>{
	static String name = "Box";
    ArrayList<T> list = new ArrayList<T>();
    T item;
    
    void setItem(T item){ this.item = item; }
    T getItem(){ return item; }
}
```

제네릭 클래스는 클래스명 뒤에 <T\>를 붙여 만들며 <> 안에 있는 변수로 T만을 사용하는 것은 아니며 이는 임의의 참조 변수를 의미하므로 어떤 것을 넣어도 문제 없다. 여기서 T는 타입 변수의 약자로 사용된 것이며 ArrayList<E\>에서 E는 Element를 의미한다. Map과 같이 여러가지 타입 변수가 필요한 경우에는 Map<K,V>와 같이 콤마로 병기한다.

### 제네릭의 제한
제네릭의 타입 변수는 static 멤버에는 사용할 수 없다. 또 제네릭 클래스의 static 멤버의 호출은 <T\>없이 원시타입으로 호출한다.
``` java
System.out.println(Box.name);
```

또한 new T[10]과 같이 제네릭 타입의 배열을 생성하는 것은 허용되지 않는다. 이는 new 연산자는 컴파일 타임에 타입이 정해져 있어야하기 때문이다. 같은 이유로 instanceof 연산자도 사용이 불가하다.

### 제네릭 클래스의 객체 생성과 사용
제네릭 객체를 생성할 때는 참조변수와 생성자에 대입된 타입이 일치해야 한다. 하지만 생성자에 대입된 타입변수는 생략할 수 있다. 어짜피 허용되지 않기 때문에 생략시 컴파일러가 알아서 참조변수의 타입을 넣어준다.

``` java
Box<Grape> b1 = new Box<Grape>();
Box<Grape> b2 = new Box<>();	// 생략 가능
```

생성된 제네릭 객체에 대입된 타입외의 다른 타입의 객체는 추가할 수 없다. 단 자손인 경우는 허용된다.
``` java
// Fruit는 Apple의 부모 클래스
Box<Fruit> b = new Box<>();

b.add(new Fruit());
b.add(new Apple());	// Ok
b.add(new String("");	// 에러
```

### 제한된 제네릭 클래스
제네릭 클래스의 T를 모든 객체가 아닌 한정된 범위로 좁힐 수 있다. 제네릭 타입에 extends 키워드를 사용하여 범위를 한정한다.
``` java
class FruitBox<T extends Fruit> { ... }
```
위의 코드는 T를 Fruit와 그 자손들만 대입할 수 있도록 설정한 것이다. 또 인터페이스의 구현한 클래스로 제한하려면 역시 extends 키워드로 표기하면 된다. 만약 둘 다 제한하려면 &로 병기한다.
``` java
// Eatable를 구현한 클래스만 타입변수로 제한
class FruitBox<T extens Eatable> { ... }
// Fruit의 자손이면서 Eatable를 구현한 클래스만 타입변수로 제한
class FruitBox<T extends Fruit & Eatable> { ... }
```

### 와일드 카드
매개변수로 과일박스 제네릭 객체를 받아 주스를 만들어 반환하는 static 메소드가 있다고 생각해보자
``` java
class Juicer {
    static Juice makeJuice(FruitBox<Apple> box){ ... }
}
```
static 메소드의 경우 제네릭 타입 변수를 사용할 수 없으므로 Apple이 아닌 다른 과일의 박스가 들어왔을경우에는 처리할 수 없다. 그렇다면 메소드 오버로딩을 통해 해결할 수 있지 않을까? 라고 생각할 수 있다.
``` java
static Juice makeJuice(FruitBox<Apple> box){ ... }
static Juice makeJuice(FruitBox<Grape> box){ ... }	// 컴파일 에러
```
하지만 컴파일하면 에러가 뜬다. 이유는
> 제네릭의 타입변수가 다르다는 이유로는 메소드 오버로딩이 허용되지 않기 때문이다.

이런 문제 때문에 나온것이 와일드카드이며 기호 ?로 나타낸다. extends로 상한선을 제한할 수 있으며 super 키워드로 하한선을 제한할 수 있다.
``` java
// Fruit와 그 자손의 제네릭 객체를 매개변수로 사용할 수 있다.
static Juice makeJuice(FruitBox<? extends Fruit> box){ ... }
```

static 메소드가 제네릭 타입변수를 사용할 수 없다는 점 때문에 예시로 들었지만 와일드카드는 statc 메소드에만 사용되는 것은 아니다. 제네릭 클래스가 아닌 클래스의 메소드나 static 메소드에 사용하면 된다.


### 제네릭 메소드
메소드의 선언부에 제네릭 타입이 선언된 메소드를 제네릭 메소드라고 한다. 이는 메소드에게 이러한 타입의 제네릭을 사용할 것을 명시한다고 이해하면 된다.
``` java
static <T> void sort(List<T> list, Comparator<? super T>) { ... }
```
> 제네릭 메소드라고 해서 제네릭 클래스 안에서만 사용 가능한 것은 아니다

static 메소드가 제네릭 타입 변수를 사용하지 못했던 것과 다르게 제네릭 메소드로 선언해주면 제네릭 타입변수의 사용이 가능하다. 위의 와일드카드 예제를 제네릭 메소드를 바꾸면 다음과 같다.
``` java
static <T extends Fruit> Juice makeJuice(FruitBox<T> box){ ... }
```

제네릭 메소드 사용시 메소드 이름 앞에 타입변수를 적어줘야만 하지만 매개변수의 타입을 통해 이를 컴파일러가 추정할 수 있으므로 생략해도 무방하다. 단 클래스 안에서 사용하거나 하는 경우(메소드만 단일로 사용)할 때는 생략할 수 없다.
``` java
System.out.println(Juicer.<FruitBox>makeJuice(fruitBox));
System.out.println(Juicer.makeJuice(fruitBox));		// 생략 가능
System.out.println(<FruitBox>makeJuice(fruitBox));	// Ok
System.out.println(makeJuice(fruitBox));		// 에러. 생략 불가능
```
