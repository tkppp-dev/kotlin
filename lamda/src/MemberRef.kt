class Person(val name: String, val age: Int){
    fun isAdult(p: Person) = this.age > p.age
    fun foobar() = println("foo")
}

fun main(){
    val foo = Person::isAdult
    val bar = Person::foobar
    println(foo(Person("tkppp", 25), Person("typpp", 15)))
    bar(Person("tkppp", 25))
}