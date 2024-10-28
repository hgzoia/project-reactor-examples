package com.hoz.project_reactor_examples

import reactor.core.publisher.Mono

class MonetizationCalculator {

    fun calculate(video: Video): Mono<Double>{
        println(video.name + " views " + video.views)
        if(video.views == null) Mono.empty<Unit>()
        else if(video.views!! < 100){
            throw RuntimeException()
        }
        return Mono.just(java.util.Random().doubles(0.0, 2000.0).findFirst().asDouble)
    }

}