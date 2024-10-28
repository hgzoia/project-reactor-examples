package com.hoz.project_reactor_examples

import com.hoz.project_reactor_examples.mock.MockVideo
import org.junit.jupiter.api.Test
import org.springframework.test.context.ContextConfiguration

@ContextConfiguration
class HotColdPublisherTest {

    @Test
    fun testColdPublisher(){
        val youtubeChannel = YoutubeChannel(MockVideo().generateVideos())

        youtubeChannel.getAllVideosName()
            .subscribe { value -> println("Subscribe 1 $value") }

        println("-----------")

        youtubeChannel.getAllVideosName()
            .subscribe { value -> println("Subscribe 2 $value") }
    }

    @Test
    fun testHotPublishAutoConnect(){
        val videoLive = VideoLive("Meetup about Java 21")
        val live = videoLive.play()

        live.subscribe { value -> println("User 1: $value") }

        Thread.sleep(2000)

        live.subscribe { value -> println("User 2: $value") }

        Thread.sleep(4000)

        live.subscribe { value -> println("User 3: $value") }

        Thread.sleep(20_000)
    }

    @Test
    fun testHotPublishAutoConnectN(){
        val videoLive = VideoLive("Meetup about Java 21")
        val live = videoLive.playN()

        Thread.sleep(2000)

        live.subscribe { value -> println("User 1: $value") }

        Thread.sleep(2000)

        live.subscribe { value -> println("User 2: $value") }

        Thread.sleep(4000)

        live.subscribe { value -> println("User 3: $value") }

        Thread.sleep(20_000)
    }

    @Test
    fun testHotPublishReSubscription(){
        val videoLive = VideoLive("Meetup about Java 21")
        val live = videoLive.playResubscription()

        live.subscribe { value -> println("User 1: $value") }

        Thread.sleep(2000)

        live.subscribe { value -> println("User 2: $value") }

        Thread.sleep(4000)

        live.subscribe { value -> println("User 3: $value") }

        Thread.sleep(20_000)
    }
}