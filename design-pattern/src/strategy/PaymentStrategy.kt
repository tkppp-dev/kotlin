package strategy

interface PaymentStrategy {
    fun pay(price: Int)
}