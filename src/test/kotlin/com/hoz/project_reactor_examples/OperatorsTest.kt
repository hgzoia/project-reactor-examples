package com.hoz.project_reactor_examples

import com.hoz.project_reactor_examples.mock.MockVideo
import org.junit.jupiter.api.Test
import org.springframework.test.context.ContextConfiguration
import reactor.core.publisher.Flux
import reactor.test.StepVerifier
import java.time.Duration

@ContextConfiguration
class OperatorsTest {

    @Test
    fun printVideos(){
        val youtubeChannel = YoutubeChannel(MockVideo().generateVideos())

        youtubeChannel
            .getAllVideos().log()
            .subscribe()

    }

    @Test
    fun printVideosTake(){
        val youtubeChannel = YoutubeChannel(MockVideo().generateVideos())

        youtubeChannel
            .getAllVideos(3)
            .subscribe()

    }

    @Test
    fun printVideosTakeWhile() {
        val youtubeChannel = YoutubeChannel(MockVideo().generateVideos())

        youtubeChannel
            .getAllVideos().log()
            .takeWhile { video -> video.likes < 10 }
            .subscribe()
    }

    @Test
    fun printDescriptionSize() {
        val youtubeChannel = YoutubeChannel(MockVideo().generateVideos())

        youtubeChannel
            .getDescriptionSize()
            .log()
            .subscribe()
    }

    @Test
    fun printVideoName() {
        val youtubeChannel = YoutubeChannel(MockVideo().generateVideos())

        youtubeChannel
            .getAllVideosName()
            .log()
            .subscribe()
    }

    @Test
    fun badFlatMap(){
        val channelList = listOf(YoutubeChannel(MockVideo().generateVideos()),
                                 YoutubeChannel(MockVideo().generateVideos2()))

        val channelFlux = Flux.fromIterable(channelList)
        channelFlux.map { channel -> channel.getAllVideosName()}.log().subscribe{item -> println(item)}
    }

    @Test
    fun flatMapVideosName(){
        val channelList = arrayListOf(YoutubeChannel(MockVideo().generateVideos()),
            YoutubeChannel(MockVideo().generateVideos2()))

        val channelFlux = Flux.fromIterable(channelList)
        channelFlux.flatMap { channel -> channel.getAllVideosName()}.log().subscribe()
    }

    @Test
    fun testFlatMapLike(){
        val videos = MockVideo().generateVideos3()
        val youtubeChannel = YoutubeChannel(videos)

        val videoFlux = youtubeChannel.getAllVideos()
                        .flatMap { video -> video.like() }
                        .map { video -> video.likes }

        StepVerifier
            .create(videoFlux)
            .expectNext(
                videos[0].likes+1,
                videos[1].likes+1,
                videos[2].likes+1)
            .verifyComplete()
    }

    @Test
    fun testFilterByRating(){
        val youtubeChannel = YoutubeChannel(MockVideo().generateVideos())

        youtubeChannel.getVideosByRating(1).log().subscribe()
    }

    @Test
    fun testPrintVideosWithDelay(){
        val youtubeChannel = YoutubeChannel(MockVideo().generateVideos2())

        val channel = youtubeChannel.getAllVideosName().log().delayElements(Duration.ofSeconds(2))

        StepVerifier
            .create(channel)
            .expectNext("Video 1", "Video 2", "Video 3")
            .verifyComplete()
    }

    @Test
    fun printVideosWithDelay(){
        val youtubeChannel = YoutubeChannel(MockVideo().generateVideos2())

        youtubeChannel.getAllVideosName().log().delayElements(Duration.ofSeconds(2)).subscribe()
        Thread.sleep(5000)
    }

    @Test
    fun simpleTransform(){
        Flux.just(1,2,3,4,5,6,7,8,9,10)
            .filter { num -> num % 2 == 0 }
            .transform(squareNumber())
            .subscribe{ result -> println(" = $result") }

        println()

        Flux.just(1,2,3,4,5,6,7,8,9,10)
            .filter { num -> num % 2 != 0 }
            .transform(squareNumber())
            .subscribe{ result -> println(" = $result") }
    }

    private fun squareNumber(): (Flux<Int>) -> Flux<Int> {
        return { flux ->
            flux
                .doOnNext { num -> print("The Square of number $num") }
                .map { num -> num * num }
        }
    }

    @Test
    fun transformExample(){
        val youtubeChannel = YoutubeChannel(MockVideo().generateVideos2())

        youtubeChannel
            .getAllVideos()
            .transform(transformMethod())
            .subscribe{ result -> println(result) }
    }

    private fun transformMethod(): (Flux<Video>) -> Flux<String>{
        return {
            flux -> flux
                .filter { video -> video.likes > 100 }
                .map { video -> video.name }
                .map { videoName -> videoName.uppercase() }
        }
    }

    @Test
    fun sideEffects(){
        val youtubeChannel = YoutubeChannel(MockVideo().generateVideos2())

        youtubeChannel
            .getAllVideosName().log()
            .doFirst { println("doFirst") }
            .doOnNext{ println("doOnNext") }
            .doOnComplete { println("doOnComplete") }
            .doAfterTerminate { println("doAfterTerminate") }
            .subscribe()
    }
}