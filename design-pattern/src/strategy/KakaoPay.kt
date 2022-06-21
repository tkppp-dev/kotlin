package strategy

class KakaoPay: PaymentStrategy {
    override fun pay(price: Int) {
        println("$price Pay completed with KakaoPay")
    }
}