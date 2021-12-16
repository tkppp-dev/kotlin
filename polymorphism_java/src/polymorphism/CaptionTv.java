package polymorphism;

public class CaptionTv extends Tv{
    public String text = "Caption";

    public CaptionTv(int channel){
        super(channel);
    }
    public void printName(){
        System.out.println("Caption!");
    }
}
