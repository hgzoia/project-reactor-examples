package com.hoz.project_reactor_examples

import com.hoz.project_reactor_examples.mock.MockVideo
import org.junit.jupiter.api.Test
import org.springframework.test.context.ContextConfiguration
import reactor.core.publisher.Flux
import reactor.util.retry.Retry
import java.time.Duration

@ContextConfiguration
class ErrorHandlerTest {

    @Test
    fun onErrorReturnMonetization(){
        val youtubeChannel = YoutubeChannel(MockVideo().generateVideos())
        val monetizationCalculator = MonetizationCalculator()

        youtubeChannel.getAllVideos()
            .flatMap { video -> monetizationCalculator.calculate(video) }
            .onErrorReturn(0.0)
            .subscribe{ value -> println("$ $value") }
    }

    @Test
    fun onErrorResumeMonetization(){
        val youtubeChannel = YoutubeChannel(MockVideo().generateVideos())
        val monetizationCalculator = MonetizationCalculator()

        youtubeChannel.getAllVideos()
            .flatMap { video -> monetizationCalculator.calculate(video) }
            .onErrorResume {
                println("onErrorResume")
                Flux.just(0.0, 999.0)
            }
            .subscribe{ value -> println("$ $value") }
    }

    @Test
    fun onErrorContinueMonetization(){
        val youtubeChannel = YoutubeChannel(MockVideo().generateVideos())
        val monetizationCalculator = MonetizationCalculator()

        youtubeChannel.getAllVideos()
            .flatMap { video -> monetizationCalculator.calculate(video) }
            .onErrorContinue { t, u ->
                val video = u as Video

                println("onErrorContinue ${video.name}")
            }
            .subscribe{ value -> println("$ $value") }
    }

    @Test
    fun onErrorMapMonetization(){
        val youtubeChannel = YoutubeChannel(MockVideo().generateVideos())
        val monetizationCalculator = MonetizationCalculator()

        youtubeChannel.getAllVideos()
            .flatMap { video -> monetizationCalculator.calculate(video) }
            .onErrorMap { _ ->
                println("onErrorMap")
                throw MonetizationException("Less than 100 views")
            }
            .subscribe{ value -> println("$ $value") }
    }

    @Test
    fun onErrorCompleteMonetization(){
        val youtubeChannel = YoutubeChannel(MockVideo().generateVideos())
        val monetizationCalculator = MonetizationCalculator()

        youtubeChannel.getAllVideos()
            .flatMap { video -> monetizationCalculator.calculate(video) }
            .onErrorComplete()
            .doFinally { signalType -> println("Sinal $signalType") }
            .subscribe{ value -> println("$ $value") }
    }

    @Test
    fun isEmptyMonetization(){
        val youtubeChannel = YoutubeChannel(MockVideo().generateVideos4())
        val monetizationCalculator = MonetizationCalculator()

        youtubeChannel.getAllVideos()
            .flatMap { video -> monetizationCalculator.calculate(video) }
            .switchIfEmpty(Flux.just(0.0))
            .subscribe{ value -> println("$ $value") }
    }

    @Test
    fun retryAnalyse(){
        val youtubeChannel = YoutubeChannel(MockVideo().generateVideos())
        val videoAnalyser = VideoAnalyser()

        youtubeChannel.getAllVideos()
            .log()
            .map { video -> videoAnalyser.analyse(video) }
            .retry(1)
            .subscribe()
    }

    @Test
    fun retryWhenAnalyse(){
        val youtubeChannel = YoutubeChannel(MockVideo().generateVideos())
        val videoAnalyser = VideoAnalyser()

        youtubeChannel.getAllVideos()
            .log()
            .map { video -> videoAnalyser.analyse(video) }
            .retryWhen(Retry.fixedDelay(2, Duration.ofSeconds(2)))
            .subscribe()

        Thread.sleep(10000)
    }
}