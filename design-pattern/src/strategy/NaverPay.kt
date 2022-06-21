package strategy

class NaverPay: PaymentStrategy {
    override fun pay(price: Int) {
        println("$price Pay completed with NaverPay")
    }
}