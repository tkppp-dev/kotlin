data class Person2(val name: String, val age: Int)

fun main() {
    val people = listOf(Person2("tkppp", 25), Person2("typpp", 15))
    println(people.filter { it.age > 20 })

    val strings = listOf("abc", "def")
    val res = strings.flatMap { it.toList() }
    println(res)

    val lists = listOf(listOf("abc", "def"), listOf("ABC", "DEF"))
    println(lists.map { list -> list.flatMap { it.toList() } }.flatten())

    println(listOf(1, 3, 4, 5, 6).asSequence().map {
        val res = (1..10).random() * it
        println("map($it) : $res")
        res
    }.filter {
        println("filter($it) : $res")
        it % 2 == 0
    })
}