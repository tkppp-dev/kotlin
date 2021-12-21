package _kotlin

import _java.SampleJavaClass

fun main(){
    val rectangleArea: Int = SampleJavaClass.rectangleArea(2, 4)
    val javaSample: SampleJavaClass = SampleJavaClass()
    val randomName: String? = javaSample.getRandom(1)

    println("in the Kotlin codes : $rectangleArea")
    // 자바 클래스의 멤버에 접근할떄도 게터와 세터 필요 X. 내부적으로 호출
    javaSample.name = "Kotlin"
    println("java member variable getter : ${javaSample.name}")
    println(randomName)
}