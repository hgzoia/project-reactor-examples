package com.hoz.project_reactor_examples

import com.hoz.project_reactor_examples.mock.MockVideo
import org.junit.jupiter.api.Test
import org.springframework.test.context.ContextConfiguration
import reactor.core.publisher.Flux
import java.time.Duration

@ContextConfiguration
class CombineResultsTest {

    @Test
    fun concatVideoNames(){
        val videoNames = YoutubeChannel(MockVideo().generateVideos3()).getAllVideosName().delayElements(Duration.ofSeconds(1)).log()
        val videoNames2 = YoutubeChannel(MockVideo().generateVideos2()).getAllVideosName().log()

        Flux.concat(videoNames, videoNames2).log().subscribe()

        Thread.sleep(5000)
    }

    @Test
    fun mergeVideoNames(){
        val videoNames = YoutubeChannel(MockVideo().generateVideos3()).getAllVideosName().delayElements(Duration.ofMillis(500)).log()
        val videoNames2 = YoutubeChannel(MockVideo().generateVideos2()).getAllVideosName().delayElements(Duration.ofMillis(300)).log()

        Flux.merge(videoNames, videoNames2).log().subscribe()

        Thread.sleep(5000)
    }

    @Test
    fun zipVideoWithMoney(){
        val videoNames = YoutubeChannel(MockVideo().generateVideos3()).getAllVideosName().delayElements(Duration.ofMillis(100)).log()
        val monetization = Flux.just(10.0, 250.0, 495.0, 780.0).delayElements(Duration.ofSeconds(8)).log()

        Flux.zip(videoNames, monetization)
            .map { tuple -> tuple.t1 + ", $ " + tuple.t2 }
            .log()
            .subscribe()

        Thread.sleep(100000)
    }

}