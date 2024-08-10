package io.github.josebatista.coffee

class CoffeeLogger {
    fun log(message: String) {
        println("[${this.hashCode()}]: $message")
    }
}
