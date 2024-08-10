package io.github.josebatista.coffee

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CoffeeLogger @Inject constructor() {
    fun log(message: String) {
        println("[${this.hashCode()}]: $message")
    }
}
