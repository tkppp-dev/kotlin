package polymorphism;

public class Tv {
    public boolean power;
    public int channel;

    public Tv(int channel){
        this.power = false;
        this.channel = channel;
    }

    void power(){
        power = !power;
    }
    void channelUp(){
        ++channel;
    }
    void channelDown(){
        --channel;
    }
    public void printName(){
        System.out.println("Tv");
    };

}
