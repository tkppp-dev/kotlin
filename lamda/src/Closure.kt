fun printProblemCounts(response: Collection<String>) {
    var clientErrors = 0;
    var serverErrors = 0;
    response.forEach {
        if (it.startsWith("4")) {
            clientErrors++
        } else if (it.startsWith("5")) {
            serverErrors++
        }
    }

    println("$clientErrors clent errors, $serverErrors server errors")
}

fun main(){
    val response = listOf("200 Ok", "404 Not found", "500 Internal Server Error")
    printProblemCounts(response)
}