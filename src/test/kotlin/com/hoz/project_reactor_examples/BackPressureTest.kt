package com.hoz.project_reactor_examples

import org.junit.jupiter.api.Test
import org.springframework.test.context.ContextConfiguration
import reactor.core.publisher.Flux
import reactor.core.publisher.FluxSink
import reactor.core.scheduler.Schedulers
import java.time.Duration
import kotlin.reflect.jvm.internal.impl.load.kotlin.JvmType
import kotlin.reflect.jvm.internal.impl.load.kotlin.JvmType.*

@ContextConfiguration
class BackPressureTest {

    @Test
    fun backPressureBuffer(){

        System.setProperty("reactor.bufferSize.small", "16")

        Flux.interval(Duration.ofMillis(1))
            .onBackpressureBuffer(20)
            .publishOn(Schedulers.single())
            .map { number ->
                Thread.sleep(500)
                println(Thread.currentThread().name + " consumindo numero: $number")
            }
            .subscribe()

        Thread.sleep(50_0000)
    }

    @Test
    fun backPressureError(){

        System.setProperty("reactor.bufferSize.small", "16")

        Flux.interval(Duration.ofMillis(1))
            .onBackpressureError()
            .publishOn(Schedulers.single())
            .map { number ->
                Thread.sleep(500)
                println(Thread.currentThread().name + " consumindo numero: $number")
            }
            .subscribe()

        Thread.sleep(50_0000)
    }

    @Test
    fun backPressureDrop(){

        System.setProperty("reactor.bufferSize.small", "16")

        Flux.interval(Duration.ofMillis(1))
            .onBackpressureDrop()
            .publishOn(Schedulers.single())
            .map { number ->
                Thread.sleep(500)
                println(Thread.currentThread().name + " consumindo numero: $number")
            }
            .subscribe()

        Thread.sleep(50_0000)
    }

    @Test
    fun backPressureLatest(){

        System.setProperty("reactor.bufferSize.small", "16")

        Flux.interval(Duration.ofMillis(1))
            .onBackpressureLatest()
            .publishOn(Schedulers.single())
            .map { number ->
                Thread.sleep(500)
                println(Thread.currentThread().name + " consumindo numero: $number")
            }
            .subscribe()

        Thread.sleep(50_0000)
    }

    @Test
    fun createOperatorBackPressureStrategies(){

        System.setProperty("reactor.bufferSize.small", "16")

        val fluxTest = Flux.create<Int>({ emitter ->
            for (i in 0..10_00000) {
                emitter.next(i)
            }
            emitter.complete()
        }, FluxSink.OverflowStrategy.LATEST)

        fluxTest
            .publishOn(Schedulers.boundedElastic())
            .map { number ->
                Thread.sleep(1)
                println(Thread.currentThread().name + " consumindo numero: $number")
            }
            .subscribe()

        Thread.sleep(10_00000)
    }
}