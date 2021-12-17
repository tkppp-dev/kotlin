포스트 주소 : https://velog.io/@tkppp-dev/%EC%97%B4%EA%B1%B0%ED%98%95Enum

# 열거형(Enum)
상수를 관리하기 위한 클래스

## 정의 및 사용
### 정의
``` java
enum EnumName {
    CONST1, CONST2, ...
}

```
### 비교
열겨형 상수는 비교연산자 '=='로 비교가 가능하다. compareTo() 메소드를 사용하여 대소비교가 가능하다.
> Enum.compareTo(Enum.CONST)의 결과는
> left(Enum) - right(Enum.CONST) 결과값이라고 생각하면 된다.

### switch 문에서의 사용
``` java
switch(dir){
    case EAST:
        ...
    case WEST:
        ...
    case NORTH:
        ...
    case SOUTH:
        ...
```
앞에 Enum 인스턴스 이름은 생략함에 유의.


### ordinal(), name()
ordinal()은 열거형 상수의 정의된 순서를 반환하고 name()은 열거형 상수의 이름을 반환한다.


## 열거형 멤버
ordinal()을 통해 열거형 상수의 값을 알 수 있지만 열거형 상수에 특정 값을 설정할 수 있다. 괄호를 통해 값을 지정하고 값을 저장할 변수와 생성자를 추가해줘야 한다.
``` java
enum Direction {
    EAST(1), WEST(-1), SOUTH(5), NORTH(10);
    
    private final int value;
    private Direction(int value){
    	this.value = value;
    }
    public int getValue() { return value; }
}
```
필요에 따라 열거형 상수에 여러 값을 지정할 수도 있다. 단 해당 값에 대한 변수와 초기화 로직, 게터 함수가 필요하다.

### 열거형에 추상메소드 추가하기

``` java
public enum Transportation {
    BUS(100) {
        public int fare(int distance) {
            return BASIC_FAIR * distance;
        }
    }, TRAIN(150) {
        public int fare(int distance) {
            return BASIC_FAIR * distance;
        }
    }, SHIP(100) {
        public int fare(int distance) {
            return BASIC_FAIR * distance;
        }
    }, AIRPLANE(300) {
        public int fare(int distance) {
            return BASIC_FAIR * distance;
        }
    };
    
    // protected로 설정해야 추상메소드 구현시 인식 가능
    protected final int BASIC_FAIR;

    private Transportation(int fair) {
        BASIC_FAIR = fair;
    }
    
    public abstract int fare(int distance);
}

```