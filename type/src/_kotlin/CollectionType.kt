package _kotlin

fun main(){
    val mc = mutableListOf(1,2,3,4)
    val c: Collection<Int> = mc

    val strings = listOf("a", "b", "c")
    val strArr = strings.toTypedArray()

    for(s in strArr){
        println(s)
    }
}