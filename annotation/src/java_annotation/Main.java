package java_annotation;

@SuppressWarnings("1111") // 유효하지 않은 어노테이션은 무시된다
@TestInfo(testedBy = "tkppp")
public class Main {
   public static void main(String[] args){
       Class<Main> cls = Main.class;
       TestInfo anno = (TestInfo) cls.getAnnotation(TestInfo.class);

       System.out.println("testCount : " + anno.count());
       System.out.println("testedBy : " + anno.testedBy());
       System.out.println("testTool : " + anno.testTools());
   }
}

