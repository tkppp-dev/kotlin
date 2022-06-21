package adapter

fun main() {
    var api = JdbcApi(MysqlDriver())
    api.findAll()

    api = JdbcApi(PostgresqlAdapter())
    api.findAll()
}