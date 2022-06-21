package observer

fun main() {
    val m1 = Member("Harry")
    val m2 = Member("Potter")
    val m3 = Member("Ron")
    val m4 = Member("Wisely")

    m1.subscribe(m2)
    m1.subscribe(m3)
    m1.subscribe(m4)

    m1.writeTwit("twit!")
}