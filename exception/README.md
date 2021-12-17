포스팅 주소 : https://velog.io/write?id=8850a569-6cb3-4fe5-9f99-9e27e37a76e6

## 자바의 예외처리
자바에서 에러는 Throwable 클래스를 상속받은 Exception과 Error 클래스로 나뉜다. Error는 발생할 경우 얄짤없이 런타임 에러를 뿜어내지만 Exception(예외)는 try-catch를 통해 핸들링이 가능하다.

``` java
try{
    throw new Exception("의도된 예외");
}catch(Exception e){
    e.printStackTrace();	// 예외가 발생한 콜스택 정보 출력
    System.out.println(e.getMessage());	    // 예외 이름 출력
}
```

try 안에서 예외가 발생하면 catch 블록에서 일치하는 예외를 찾는다. 일치하는 예외가 없다면 런타임 에러가 발생하고 일치하는 에러가 있다면 해당 catch 블록에서 예외를 처리하고 프로세스를 종료시키지 않는다.

### Multi Catch Exception
여러 예외를 한 catch 블록에서 처리하기 위해서는 예외를 '|'로 병기할 수 있다. 단 상속 관계에 있는 에러끼리는 같이 쓸 수 없다. 서로 관계 없는 예외 끼리만 사용 가능하다.

``` java

try{
    if(n%2 == 0 ){
          System.out.println(1/0);
    }
    else{
          throw new NullPointerException();
    }
}catch (ArithmeticException | NullPointerException e){
    e.printStackTrace();
}
```

### Method Exception - Throws
자바의 예외처리가 짜증나는 이유. 메서드에 throws로 예외를 선언해주면 예외 처리를 해당 메서드를 사용하는 곳에서 하도록 강제한다. 예외 처리를 안하면 얄짤없이 컴파일 시점에서 컷당하기 때문에 예외를 처리해줘야 한다.

``` java
public void method1(){
   try{
       method2();
   }catch (Exception e){
        System.out.println(e.getMessage() + " : 에러처리를 method1에서 완료");
    }
}

public void method2() throws Exception{
    throw new Exception("에러처리를 method1에 위임");
}
```

> 예외를 던지는 **throw**와 예외가 발생할 수 있음을 알리는 **throws**를 구분해야한다.

### 연결된 예외
어떤 예외가 발생했을때 그 예외를 상위 예외의 원인으로 지정하여 넘겨줄 수 있다. 예를 들면 (가상의 예외) SpaceException과 MemoryException이 Install중 발생하면 InstallException으로 처리하고 싶을 경우 예외를 연결할 수 있다.

> Exception.initCause()로 예외의 원인을 지정할 수 있다.

``` java
public void install() throws Exception{
    try{
        throw new SpaceException("의도된 예외");
    }catch (SpaceException | MemoryException ex){
        InstallException e = new InstallException();
        e.initCause(ex);
        throw e;
    }
}

public void installFile(){
     try{
        install();
     }catch (Exception e){
        e.printStackTrace();
    }
}
```
