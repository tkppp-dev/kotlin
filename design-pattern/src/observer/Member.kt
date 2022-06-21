package observer

class Member(val name: String, private val followers: MutableList<Follower> = mutableListOf()) : Leader, Follower {
    override fun subscribe(follower: Follower) {
        followers.add(follower)
    }

    override fun unsubscribe(follower: Follower) {
        followers.remove(follower)
    }

    override fun notifyMember(msg: String) {
        followers.forEach { it.update(msg) }
    }

    override fun update(msg: String) {
        println("$name - $msg")
    }

    fun writeTwit(twit: String){
        notifyMember("$name twit: $twit")
    }
}