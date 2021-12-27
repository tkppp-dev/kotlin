data class Person(val name: String, val age: Int)

fun main(){
    val p1 = Person("tkppp", 24)
    val p2 = Person("tkppp", 35)

    println("str1 == str2 : ${p1.name == p2.name}")
    println("str1 === str2 : ${p1.name === p2.name}")


}