package com.hoz.project_reactor_examples

import reactor.core.publisher.Mono
import reactor.core.scheduler.Schedulers
import java.util.Random

class VideoAnalyser {

    fun analyse(video: Video): Double{
        val rate = Random().doubles(1.0, 15.0).findFirst().asDouble
        println(video.name + " rate $rate")
        if(rate > 10)
            throw RuntimeException("An unexpected error occurred")

        return rate
    }

    fun analyseBlocking(video: Video): Double{
        Thread.sleep(3000)
        val rate = Random().doubles(1.0, 10.0).findFirst().asDouble
        println(video.name + " rate $rate" + "Thread ${Thread.currentThread().name}")
        if(rate > 10)
            throw RuntimeException("An unexpected error occurred")

        return rate
    }

    fun analyseBlockingMono(video: Video): Mono<Double>{
        return Mono.fromCallable { analyseBlocking(video) }
            .publishOn(Schedulers.boundedElastic())
    }
}