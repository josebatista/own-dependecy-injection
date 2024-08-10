package io.github.josebatista.coffee

class ElectricHeater(private val logger: CoffeeLogger) : Heater {
    override var isHot = false
        private set

    override fun on() {
        isHot = true
        logger.log("~ ~ ~ heating ~ ~ ~")
    }

    override fun off() {
        isHot = false
        logger.log("~ ~ ~ cooling ~ ~ ~")
    }
}
