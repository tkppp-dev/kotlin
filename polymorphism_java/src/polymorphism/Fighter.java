package polymorphism;

public class Fighter implements Fightable{
    @Override
    public void attak() {
        System.out.println("Attack!");
    }
}
