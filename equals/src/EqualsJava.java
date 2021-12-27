public class EqualsJava {
    public static void main(String[] args){
        String s1 = "tkppp";
        String s2 = new String("tkppp");

        System.out.println("str1 == str2 : " + (s1 == s2));
        System.out.println("str1 == str2 : " + s1.equals(s2));
    }
}
