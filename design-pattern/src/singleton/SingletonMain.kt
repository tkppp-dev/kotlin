package singleton

fun main(){
    val s1 = Foo
    val s2 = Bar.getInstance()

    s1.name = "new Foo"
    s2.name = "new Bar"

    println(Foo.name)
    println(Bar.getInstance().name)
}