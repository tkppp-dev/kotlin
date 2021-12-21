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
