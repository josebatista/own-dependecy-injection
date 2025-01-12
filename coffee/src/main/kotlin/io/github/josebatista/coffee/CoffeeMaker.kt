package io.github.josebatista.coffee

import javax.inject.Inject

class CoffeeMaker @Inject constructor(
    private val logger: CoffeeLogger,
    private val heater: Heater,
    private val pump: Pump
) {
    fun brew() {
        heater.on()
        pump.pump()
        logger.log(" [_]P coffee! [_]P")
        heater.off()
    }
}
