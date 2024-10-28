package com.hoz.project_reactor_examples

import com.hoz.project_reactor_examples.mock.MockVideo
import org.junit.jupiter.api.Test
import org.springframework.test.context.ContextConfiguration
import reactor.core.scheduler.Scheduler
import reactor.core.scheduler.Schedulers

@ContextConfiguration
class SchedulerTest {

    @Test
    fun blockingOperation(){
        val youtubeChannel = YoutubeChannel(MockVideo().generateVideos())

        val videos = youtubeChannel.getAllVideos()
            .filter { video ->
                println("Filter 1 - Thread: ${Thread.currentThread().name}")
                video.description.length > 10}
            .map { video ->
                println("Map 1 - Thread: ${Thread.currentThread().name}")
                video.description
            }
            .map { description ->
                Thread.sleep(5000)
                println("Map 2 - Thread: ${Thread.currentThread().name}")
                description.uppercase()
            }

            for(i in 0..2){
                println("Execution $i")
                videos.subscribe { description -> println(description) }
            }
            Thread.sleep(20_000)
    }

    @Test
    fun publishOnBlockingOperation(){
        val youtubeChannel = YoutubeChannel(MockVideo().generateVideos2())

        val videos = youtubeChannel.getAllVideos()
            .filter { video ->
                println("Filter 1 - Thread: ${Thread.currentThread().name}")
                video.description.length > 10}
            .map { video ->
                println("Map 1 - Thread: ${Thread.currentThread().name}")
                video.description
            }
            .publishOn(Schedulers.boundedElastic())
            .map { description ->
                Thread.sleep(5000)
                println("Map 2 - Thread: ${Thread.currentThread().name}")
                description.uppercase()
            }

        for(i in 0..10){
            println("Execution $i")
            videos.subscribe { description -> println(description) }
        }
        Thread.sleep(20_000)
    }

    @Test
    fun subscribeOnBlockingOperation(){
        val youtubeChannel = YoutubeChannel(MockVideo().generateVideos2())

        val videos = youtubeChannel.getAllVideos()
            .filter { video ->
                println("Filter 1 - Thread: ${Thread.currentThread().name}")
                video.description.length > 10}
            .map { video ->
                println("Map 1 - Thread: ${Thread.currentThread().name}")
                video.description
            }
            .subscribeOn(Schedulers.boundedElastic())
            .map { description ->
                Thread.sleep(5000)
                println("Map 2 - Thread: ${Thread.currentThread().name}")
                description.uppercase()
            }

        for(i in 0..10){
            println("Execution $i")
            videos.subscribe { description -> println(description) }
        }
        Thread.sleep(20_000)
    }

    @Test
    fun parellelBlockingOperation(){
        val youtubeChannel = YoutubeChannel(MockVideo().generateVideos2())
        val videoAnalyser = VideoAnalyser()

        youtubeChannel.getAllVideos()
            .filter { video ->
                println("Filter 1 - Thread: ${Thread.currentThread().name}")
                video.description.length > 10}
            .parallel().runOn(Schedulers.boundedElastic())
            //.publishOn(Schedulers.parallel())
            .map { video ->
                videoAnalyser.analyseBlocking(video)
            }
            .subscribe()

        Thread.sleep(30000)
    }

    @Test
    fun parellelPublishOnBlockingOperation(){
        val youtubeChannel = YoutubeChannel(MockVideo().generateVideos2())
        val videoAnalyser = VideoAnalyser()

        youtubeChannel.getAllVideos()
            .filter { video ->
                println("Filter 1 - Thread: ${Thread.currentThread().name}")
                video.description.length > 10}
            .flatMap { video -> videoAnalyser.analyseBlockingMono(video) }
            .subscribe()

        Thread.sleep(30000)
    }

}