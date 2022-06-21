package adapter

class JdbcApi(private val driver: JdbcDriver) {
    fun findAll() {
        driver.findAll()
    }
}