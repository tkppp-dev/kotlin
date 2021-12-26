package _java;

public class Person {
    public static void yellAt(Person person) {
        System.out.println(person.name.toUpperCase() + "!!!");
    }

    private final String name;

    public Person(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
