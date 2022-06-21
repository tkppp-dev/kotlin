package proxy

class Proxy() : FooService {
    lateinit var service: FooService

    override fun runSomething() {
        println("Proxy Running...")
        service.runSomething()
    }
}