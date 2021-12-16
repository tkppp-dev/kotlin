package polymorphism;

public class Main {
    public static void main(String[] args){
        Polymorphism c = new Polymorphism();
        CaptionTv t = new CaptionTv(14);

        c.polymorphism(t);
        c.casting();
    }
}
