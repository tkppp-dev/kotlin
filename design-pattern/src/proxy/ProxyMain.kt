package proxy

fun main() {
    // no proxy
    var service: FooService = ServiceA()
    service.runSomething()
    service = ServiceB()
    service.runSomething()

    // with proxy
    val proxy = Proxy()
    proxy.service = ServiceA()
    proxy.runSomething()
    proxy.service = ServiceB()
    proxy.runSomething()
}