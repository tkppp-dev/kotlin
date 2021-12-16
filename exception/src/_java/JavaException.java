package _java;

import com.sun.jdi.VMOutOfMemoryException;

public class JavaException {
    /**
     * Object - Throwable - Exception
     *                    \
     *                      Error
     * Error 발생시 얄짤없이 런타임 에러
     * Exception은 예외처리로 핸들링 할 수 있다.
     */

    // 일치하는 catch block을 찾으면 예외처리
    public void multiBlockException(int n){
        try{
            if(n%2 == 0){
                System.out.println(1/0);
            }else{
                throw new Exception("Custom Exception");
            }
        }catch (ArithmeticException ae){
            ae.printStackTrace();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    // 여러 종류의 예외를 한번에 잡을 때 사용, 단 상속 관계에 있는 에외는 에러
    public void multiCatchException(int n){
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
    }

    // 메서드 예외처리 throws : 메서드 사용자에게 예외처리를 강제 => 이 에러가 뜰 수 있으니 알아서 예외처리 하고 사용하세요 라는 뜻
    // 예외를 던질때 사용하는 throw와 throws를 구별해야 함.
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

    // 연결된 예외 : 예외를 다른 예외의 원인으로 지정하고 넘겨줄 수 있음
    public void install() throws Exception{
        try{
            throw new ArithmeticException("의도된 예외");
        }catch (ArithmeticException ae){
            Exception e = new Exception();
            e.initCause(ae);
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
}
