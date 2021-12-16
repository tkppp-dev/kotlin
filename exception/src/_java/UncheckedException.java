package _java;

// RuntimeException은 예외처리를 강제하지 않는 예외로 RuntimeException을 상속받아 만든다.
public class UncheckedException extends RuntimeException{
    public UncheckedException(String msg){
        super(msg);
    }
}
