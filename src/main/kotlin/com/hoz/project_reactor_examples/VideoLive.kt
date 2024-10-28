package com.hoz.project_reactor_examples

import reactor.core.publisher.Flux
import java.time.Duration

data class VideoLive(
    private var title: String
) {

    fun play(): Flux<String> {
        return Flux.interval(Duration.ofMillis(500))
            .map { value -> getLiveEvent(value) }
            .takeWhile { event -> event != "End." }
            .publish().autoConnect()
    }

    fun playN(): Flux<String> {
        return Flux.interval(Duration.ofMillis(500))
            .map { value -> getLiveEvent(value) }
            .takeWhile { event -> event != "End." }
            .publish().autoConnect(2)
    }

    fun playResubscription(): Flux<String> {
        return Flux.interval(Duration.ofMillis(500))
            .map { value -> getLiveEvent(value) }
            .takeWhile { event -> event != "End." }
            .share()
    }

    private fun getLiveEvent(sequence: Long): String {
        return when (sequence.toInt()) {
            0 -> {
                "Livestream starting..."
            }
            1 -> {
                "New feature announced..."
            }
            2 -> {
                "Talking with viewers..."
            }
            3 -> {
                "Giveaway time..."
            }
            4 -> {
                "Next event announced..."
            }
            7 -> {
                "Livestream ending..."
            }
            8 -> {
                "End."
            }
            else -> {
                "Live in progress..."
            }
        }
    }


}