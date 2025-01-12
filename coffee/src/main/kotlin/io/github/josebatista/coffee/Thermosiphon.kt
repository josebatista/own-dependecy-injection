package io.github.josebatista.coffee

import javax.inject.Inject

class Thermosiphon @Inject constructor(private val logger: CoffeeLogger, private val heater: Heater) : Pump {
    override fun pump() {
        if (heater.isHot) {
            logger.log("=> => pumping => =>")
        }
    }
}
