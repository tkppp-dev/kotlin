package adapter

class MysqlDriver : JdbcDriver {
    private val name = "MySQL"

    override fun findAll() {
        println("$name - Find all")
    }
}