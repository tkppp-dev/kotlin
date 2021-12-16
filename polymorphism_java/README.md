포스팅 주소 : https://velog.io/@tkppp-dev/java-polymorphism

## 다형성이란?
다형성(Polymorphism)이란 '여러가지 형태를 가질 수 있는 능력'을 의미하며 자바에서는 한 타입의 참조변수로 여러 타입의 객체를 참조할 수 있도록 하여 다형성을 구현하였다.

### 캐스팅
> 업캐스팅 : 자식의 참조변수를 부모의 참조변수로 타입캐스팅하는 것(생략 가능)
> 
> 다운캐스팅 : 부모의 참조변수를 자식의 참조변수로 타입캐스팅하는 것(생략 불가)

자바를 공부하면서 업캐스팅과 다운캐스팅에 대해 항상 헷갈렸었다. 어떨 때는 다운캐스팅이 안되지만 어떨 때는 또 된다.

그 이유는 자손 참조객체에 부모 인스턴스를 할당하는 것은 불가능하지만 자손 인스턴스를 업캐스팅했다가 다운캐스팅 하는 것은 가능하다는 차이를 이해하지 못했기 때문이다.


``` java
public class Tv {
    public boolean power;
    public int channel;

    public Tv(int channel){
        this.power = false;
        this.channel = channel;
    }

    public void printName(){
        System.out.println("Tv");
    };
}

public class CaptionTv extends Tv{
    public String text = "Caption";

    public CaptionTv(int channel){
        super(channel);
    }
    public void printName(){
        System.out.println("Caption!");
    }
}

public class AppleTv extends Tv{
    public String text = "Apple";

    public AppleTv(int channel){
        super(channel);
    }
    public void printName(){
        System.out.println("Apple!");
    }
}

```

``` java 
public Tv t1 = new Tv(10);
public CaptionTv t2 = new CaptionTv(12);
// public CaptionTv t3 = new Tv();  컴파일 에러

public void casting(){
    Tv temp1 = t2;   // 업캐스팅 생략 가능
    CaptionTv temp2 = (CaptionTv) t2;   // 다운 캐스팅 생략 불가
    System.out.println("type casting : " + temp2.text);
    //CaptionTv temp3 = (CaptionTv) t1;   // 컴파일 에러. 부모 인스턴스는 다운캐스팅 불가
}
```

### 다형성 예제

> 그럼 대체 객체의 타입 캐스팅을 대체 왜 할까?

리팩토링 과목을 들으면서 교수님이 귀에 박히도록 하신 말씀이 바로 다형성. 불필요하고 장황한 코드를 줄이는 가장 효과적인 방법이라고 하셨다.


1. 어떤 메서드의 파라미터로 부모는 같지만 다른 자식 객체들이 들어오는 경우
2. 부모는 같지만 다른 자식들을 담은 배열 혹은 리스트가 필요한 경우

1번의 경우 파라미터를 부모로 업캐스팅해서 받아 다시 다운캐스팅하여 사용할 수 있다.

``` java
// 여전히 지저분하다
public void polymorphism(Tv t){
    if(t instanseof AppleTv){
    	(AppleTv)t.printName();
    }
    else if(t instanseof CaptionTv){
    	(CaptionTv)t.printName();
    }
}
```
이런 식으로 코드를 구현할 수 있다. 다형성을 이용해 구현했는데도 코드가 지저분하고 중복이 많다. 어떻게 하면 코드를 간단하게 만들까? 바로 추상 메서드 혹은 메서드 오버라이딩이다.

Tv 클래스에서 printName 메서드를 정의해놓거나 추상메서드로 선언한다면 다운캐스팅 없이 부모 참조변수로 바로 자식의 메서드로 접근할 수 있다.

``` java
public void polymorphism(Tv t){
    t.printName()
}
```

> 인스턴스 메서드는 오버라이딩시 참조 변수의 타입과 관계없이 가리키는 인스턴스의 메서드를 실행한다


2번의 코드는 아래와 같다
``` java
Tv[] arr = new Tv[2];
arr[0] = new CaptionTv(10);
arr[1] = new AppleTv(11);

for(Tv item: arr){
    item.printName();
}
```

### 추상화
abstact 키워드를 통해 클래스와 메서드를 추상화할 수 있다.
> 추상 클래스 : 추상 메서드를 가진 클래스로 인스턴스화가 불가능하다. 만약 부모 클래스도 인스턴스화 하고 싶다면 추상 메서드를 사용해 추상화하는 것이 아닌 메서드 오버라이딩을 사용해야 한다.
>
> 추상 메서드 : 부모에 선언부만 작성된 메서드로 자식 클래스에 선언부의 본문을 정의한 메서드를 작성할 것을 강제한다.

### 인터페이스
인터페이스는 일종의 추상클래스로 추상화 정도가 높아서 몸통을 가진 일반 메서드를 가질 수 없다.

> 인터페이스 규칙
> - 모든 멤버변수는 public static final 이어야 하며, 이는 생략 가능하다.
> - 모든 메서드는 public abstract 이어야 하며, 이는 생략 가능하다.

추상화를 위한 도구이기 때문에 public, abstract 키워드가 강제되지만 모든 변수, 메서드에 예외없이 적용되는 사항이기 때문에 생략이 가능하다. 단 본문을 가진 default 메서드와 static 메서드는 이를 표시해주어야 한다.

``` java
public interface InterfaceName {
    public static final memberVariable;
    public abstract void method1();
    void method2();	// public, abstract 생략 가능
    static void method3() { ... }    // public만 생략 가능
    defualt void method3() { ... }    // public만 생략 가능
}
```

### 인터페이스를 이용한 다형성
인터페이스로 구현된 클래스는 인터페이스 참조변수를 통해 참조될 수 있다. 인터페이스 참조변수는 인터페이스로 구현된 멤버변수, 메서드만 사용할 수 있음에 유의해야 한다.
``` java
public interface Fightable { ... }
public class Fighter { ... }

public Fightable = new Fighter();
 또는
public Fightable = (Fightable)new Fighter();
```
> 인터페이스를 구현할 때는 @Override 어노테이션을 사용하여 구현했다는 것을 표기하는 것이 좋다.
>
> 메서드의 파라미터로 인터페이스 참조변수를 사용하거나 메서드의 반환 타입을 인터페이스로 지정하는 등의 방식으로 다형성을 구현할 수 있다

클래스와 마찬가지로 캐스팅이 필요하다.
> - 인터페이스 참조변수에서 클래스 참조변수로 캐스팅할 경우 생략 불가
> - 클래스 인스턴스를 인터페이스 참조변수에 할당할 때는 생략 가능