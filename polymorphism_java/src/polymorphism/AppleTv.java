package polymorphism;

public class AppleTv extends Tv{
    public String text = "Apple";

    public AppleTv(int channel){
        super(channel);
    }
    public void printName(){
        System.out.println("Apple!");
    }
}
