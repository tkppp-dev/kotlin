package decorator

class Decorator : FooService {
    lateinit var service: FooService

    override fun runSomething(): String
        = "With decorator " + service.runSomething()
}