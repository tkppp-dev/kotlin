package _kotlin

import _java.NullableClass

fun getIsNull(n: Int): String? = when(n % 2 == 0){
    true -> "I'm not null"
    else -> null
}

fun main() {
    for (i in 0 until 5) {
        val res = getIsNull(i)
        val res2: String = NullableClass.getIsNull(i)
        println("res is String : ${res is String}, or String? : ${res is String?}")
        println("res2 is String : ${res2 is String}, or String? : ${res2 is String?}")
        println("$i : ${res2.length}")
    }
}