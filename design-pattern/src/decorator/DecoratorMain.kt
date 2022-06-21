package decorator

fun main() {
    val service: FooService = ServiceA()
    val decorator = Decorator()
    decorator.service = service

    println(service.runSomething())
    println(decorator.runSomething())
}