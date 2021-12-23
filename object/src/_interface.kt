interface Clickable {
    fun click()
    fun showOff() = println("I'm clickable")
}

interface Focusable {
    fun showOff() = println("I'm focusable")
}

class Button : Clickable, Focusable {
    override fun click() {}
    override fun showOff() {
        println("I'm button")
    }
}

fun main(){
    val btn = Button()
    btn.showOff()
}