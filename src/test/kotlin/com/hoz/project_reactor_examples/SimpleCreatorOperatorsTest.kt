package com.hoz.project_reactor_examples

import org.junit.jupiter.api.Test
import org.springframework.test.context.ContextConfiguration
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.test.StepVerifier
import java.time.Duration
import java.util.stream.Stream

@ContextConfiguration
class SimpleCreatorOperatorsTest {

    @Test
    fun fluxJust(){
        val simpleFlux = Flux.just(1,2,3,4).delayElements(Duration.ofMillis(1000)).log()

        StepVerifier
            .create(simpleFlux)
            .expectNext(1,2,3,4)
            .verifyComplete()
    }

    @Test
    fun fluxFromIterable(){
        val simpleFlux = Flux.fromIterable(listOf(1,2,3,4)).log()

        StepVerifier
            .create(simpleFlux)
            .expectNext(1,2,3,4)
            .verifyComplete()
    }

    @Test
    fun fluxFromArray(){
        val simpleFlux = Flux.fromArray(arrayOf(1,2,3,4)).log()

        StepVerifier
            .create(simpleFlux)
            .expectNext(1,2,3,4)
            .verifyComplete()
    }

    @Test
    fun fluxFromStream(){
        val simpleFlux = Flux.fromStream(Stream.of(1,2,3,4)).log()

        StepVerifier
            .create(simpleFlux)
            .expectNext(1,2,3,4)
            .verifyComplete()
    }

    @Test
    fun fluxRange(){
        val simpleFlux = Flux.range(1,4).log()

        StepVerifier
            .create(simpleFlux)
            .expectNext(1,2,3,4)
            .verifyComplete()
    }

    @Test
    fun monoJust(){
        val monoJust = Mono.just(1).log()

        StepVerifier
            .create(monoJust)
            .expectNext(1)
            .verifyComplete()
    }

}