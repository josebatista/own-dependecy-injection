package io.github.josebatista

import io.github.josebatista.coffee.CoffeeLogger
import io.github.josebatista.coffee.CoffeeMaker
import io.github.josebatista.coffee.ElectricHeater
import io.github.josebatista.coffee.Heater
import io.github.josebatista.coffee.Pump
import io.github.josebatista.coffee.Thermosiphon
import io.github.josebatista.di.FactoryHolderModule
import io.github.josebatista.di.ObjectGraph
import io.github.josebatista.di.ReflectiveModule
import io.github.josebatista.di.extension.bind
import io.github.josebatista.di.extension.get
import io.github.josebatista.di.extension.install
import io.github.josebatista.di.extension.installSingleton
import kotlin.time.measureTimedValue

fun main() {

    val (coffeeMaker1, manualTime) = createWithManualDI()
    coffeeMaker1.brew()
    println("[1- MANUAL]: $manualTime\n\n")

    val (coffeeMaker2, factorHolderModuleTime) = createWithFactoryHolderModule()
    coffeeMaker2.brew()
    println("[2- FACTORY HOLDER MODULE]: $factorHolderModuleTime\n\n")

    val (coffeeMaker3, factorHolderModulesTime) = createWithFactoryHolderModules()
    coffeeMaker3.brew()
    println("[3- FACTORY HOLDER MODULE]: $factorHolderModulesTime\n\n")

    val (coffeeMaker4, reflectiveTime) = createWithReflectiveModule()
    coffeeMaker4.brew()
    println("[4- REFLECTIVE MODULE]: $reflectiveTime\n\n")
}

private fun createWithManualDI() = measureTimedValue {
    val logger = CoffeeLogger()
    val heater: Heater = ElectricHeater(logger = logger)
    val pump: Pump = Thermosiphon(logger = logger, heater = heater)
    CoffeeMaker(logger = logger, heater = heater, pump = pump)
}

private fun createWithFactoryHolderModule() = measureTimedValue {
    val module = FactoryHolderModule().apply {
        bind<Heater, ElectricHeater>()
        bind<Pump, Thermosiphon>()
        installSingleton { CoffeeLogger() }
        installSingleton { ElectricHeater(logger = get()) }
        install { Thermosiphon(logger = get(), heater = get()) }
        install { CoffeeMaker(logger = get(), heater = get(), pump = get()) }
    }
    val objectGraph = ObjectGraph(module)
    objectGraph.get<CoffeeMaker>()
}

private fun createWithFactoryHolderModules() = measureTimedValue {
    val logModule = FactoryHolderModule().apply {
        installSingleton { CoffeeLogger() }
    }
    val partsModule = FactoryHolderModule().apply {
        installSingleton<Heater> { ElectricHeater(get()) }
        install<Pump> { Thermosiphon(get(), get()) }
    }
    val appModule = FactoryHolderModule().apply {
        install { CoffeeMaker(get(), get(), get()) }
    }
    val objectGraph = ObjectGraph(logModule, partsModule, appModule)
    objectGraph.get<CoffeeMaker>()
}

private fun createWithReflectiveModule() = measureTimedValue {
    val objectGraph = ObjectGraph(
        FactoryHolderModule().apply {
            bind<Heater, ElectricHeater>()
            bind<Pump, Thermosiphon>()
        },
        ReflectiveModule()
    )
    objectGraph.get<CoffeeMaker>()
}