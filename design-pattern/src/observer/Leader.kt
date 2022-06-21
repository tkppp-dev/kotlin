package observer

interface Leader {
    fun subscribe(follower: Follower)
    fun unsubscribe(follower: Follower)
    fun notifyMember(msg: String)
}