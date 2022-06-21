package proxy

class ServiceB : FooService {
    override fun runSomething() {
        println("ServiceB running")
    }
}