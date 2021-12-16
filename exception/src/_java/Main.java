package _java;

public class Main {
    public static void main(String[] args){
        JavaException je = new JavaException();
        je.multiBlockException(1);
        je.multiBlockException(2);

        je.multiCatchException(1);
        je.multiCatchException(2);

        je.method1();

        je.installFile();
    }
}
