data class Person(val name: String, val age: Int, val position: String)

object Payroll {
    val allEmployees = arrayListOf<Person> ()
    fun calculateSalary(){
        println("calculate Salary!")
    }
}

fun main(){
    Payroll.calculateSalary()
}