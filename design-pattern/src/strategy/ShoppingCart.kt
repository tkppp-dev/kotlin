package strategy

class ShoppingCart(
    val items: MutableList<Int> = mutableListOf()
) {

    fun getTotalPrice(): Int = items.fold(0) { total, price -> total + price }

    fun pay(paymentMethod: PaymentStrategy) {
        paymentMethod.pay(getTotalPrice())
    }
}