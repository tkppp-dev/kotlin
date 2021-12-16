package polymorphism;

public class Polymorphism {
    //public Tv t1 = new Tv(10);
    public CaptionTv t2 = new CaptionTv(12);
    // public CaptionTv t3 = new Tv();  컴파일 에러

    public void casting(){
        Tv temp1 = t2;   // 업캐스팅 생략 가능
        CaptionTv temp2 = (CaptionTv) t2;   // 다운 캐스팅 생략 불가
        System.out.println("type casting : " + temp2.text);
        //CaptionTv temp3 = (CaptionTv) t1;   // 컴파일 에러. 부모 인스턴스는 다운캐스팅 불가
    }

    public void polymorphism(Tv t){
        CaptionTv temp = (CaptionTv) t;
        System.out.println("polymorphism : " + temp.text);

        Tv[] arr = new Tv[2];
        arr[0] = new CaptionTv(10);
        arr[1] = new AppleTv(11);

        for(Tv item: arr){
            item.printName();
        }
    }

    public Fightable returnInterface(){
        return new Fighter();
    }

    public void _interface(){
        Fighter f = (Fighter) returnInterface();
    }

}
