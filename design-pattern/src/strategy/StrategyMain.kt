package strategy

fun main() {
    val cart = ShoppingCart()
    cart.items.addAll(listOf(100, 500, 700))

    cart.pay(KakaoPay())
    cart.pay(NaverPay())
}