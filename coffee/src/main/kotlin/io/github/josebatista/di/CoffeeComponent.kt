package io.github.josebatista.di

import io.github.josebatista.coffee.CoffeeMaker
import io.github.josebatista.coffee.ElectricHeater
import io.github.josebatista.coffee.Heater
import io.github.josebatista.coffee.Pump
import io.github.josebatista.coffee.Thermosiphon

@Component(modules = [CoffeeBindsModule::class])
interface CoffeeComponent {
    val coffeeMaker: CoffeeMaker
}

interface CoffeeBindsModule {
    @Binds
    fun bindHeater(heater: ElectricHeater): Heater

    @Binds
    fun bindPump(pump: Thermosiphon): Pump
}