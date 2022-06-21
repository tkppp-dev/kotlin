package adapter

class PostgresqlAdapter : JdbcDriver {
    val driver = PostgresqlDriver()

    override fun findAll() {
        driver.findAll()
    }
}