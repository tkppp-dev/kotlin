package _java;

// CheckedException은 예외처리를 강제하는 예뢰로 Exception 객체를 상속받아 만든다
public class CheckedException extends Exception{
    public CheckedException(String msg){
        super(msg);
    }
}
