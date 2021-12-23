interface User {
    val nickname: String
}

fun getFacebookName(accountId: Int) = "tkppp"

// 주 생성자에서 구현
class PrivateUser(override val nickname: String) : User

// 필드와 커스텀 게터를 이용한 구현
class SubscribeUser(val email: String) : User {
    override val nickname
        get() = email.substringBefore('@')
}

// 필드에 구현
class FacebookUser(val accountId: Int) : User {
    override val nickname = getFacebookName(accountId)
}

data class Test(val name: String = "tkppp", val age: Int = 25)

fun main(){
    val user = SubscribeUser("gowldla0423@naver.com")
    val me = Test()
    println(me)
    println(user.nickname)
}
