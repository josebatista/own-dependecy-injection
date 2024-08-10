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
import kotlin.time.measureTime

fun main() {

    // Manual Inject
    val manualTime = measureTime {
        val logger = CoffeeLogger()
        val heater: Heater = ElectricHeater(logger = logger)
        val pump: Pump = Thermosiphon(logger = logger, heater = heater)
        val coffeeMaker = CoffeeMaker(logger = logger, heater = heater, pump = pump)
        coffeeMaker.brew()
    }
    println("[MANUAL]: $manualTime")

//    //ObjectGraph
//    val objectGraph = ObjectGraph()
//    val coffeeMaker1 = objectGraph.get(CoffeeMaker::class.java)
//    val coffeeMaker2 = objectGraph[CoffeeMaker::class.java]
//
//    // reified Extension
//    val coffeeMaker3 = objectGraph.get<CoffeeMaker>()
//    val coffeeMaker4: CoffeeMaker = objectGraph.get()
//
//    // module
//    val module = FactoryHolderModule()
//    module.install(CoffeeMaker::class.java) { objectGraphFactory ->
//        CoffeeMaker(objectGraphFactory.get(), objectGraphFactory.get(), objectGraphFactory.get())
//    }
//
//    val module1 = FactoryHolderModule()
//    module1.install { CoffeeMaker(get(), get(), get()) }

    println("\n===============\n")

    // Library Injection
    val libraryTime = measureTime {
        val module = FactoryHolderModule().apply {
            bind<Heater, ElectricHeater>()
            bind<Pump, Thermosiphon>()
            installSingleton { CoffeeLogger() }
            installSingleton { ElectricHeater(logger = get()) }
            install { Thermosiphon(logger = get(), heater = get()) }
            install { CoffeeMaker(logger = get(), heater = get(), pump = get()) }
        }
        val objectGraph = ObjectGraph(module)
        val coffeeMaker1: CoffeeMaker = objectGraph.get()
        coffeeMaker1.brew()
    }
    println("[LIBRARY]: $libraryTime")

    println("\n===============\n")

    // Reflection (Guice)
    val reflectiveTime = measureTime {
        val bindingModule = FactoryHolderModule().apply {
            bind<Heater, ElectricHeater>()
            bind<Pump, Thermosiphon>()
        }

        val objectGraph1 = ObjectGraph(
            bindingModule,
            ReflectiveModule()
        )
        val coffeeMaker2: CoffeeMaker = objectGraph1.get()
        coffeeMaker2.brew()
    }
    println("[REFLECTIVE]: $reflectiveTime")
}