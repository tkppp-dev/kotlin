package _kotlin

import _java.Person

fun yellAt(p: Person){
    println(p.name.uppercase() + "!!!")
}

fun main(){
    //Person.yellAt(Person(null))
    yellAt(Person(null))
}