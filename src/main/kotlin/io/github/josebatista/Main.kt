package io.github.josebatista

import io.github.josebatista.coffee.*

fun main() {

    // Manual Inject
    val logger = CoffeeLogger()
    val heater: Heater = ElectricHeater(logger = logger)
    val pump: Pump = Thermosiphon(logger = logger, heater = heater)
    val coffeeMaker = CoffeeMaker(logger = logger, heater = heater, pump = pump)

    coffeeMaker.brew()
}