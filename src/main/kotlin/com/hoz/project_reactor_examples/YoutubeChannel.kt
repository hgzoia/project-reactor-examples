package com.hoz.project_reactor_examples

import reactor.core.publisher.Flux

data class YoutubeChannel(
    private var videos: MutableList<Video>
){
    fun addVideo(video: Video){
        videos.add(video)
    }

    fun getAllVideos(): Flux<Video> {
        return Flux.fromIterable(videos)
    }

    fun getAllVideos(number: Long): Flux<Video> {
        return Flux.fromIterable(videos).log().take(number)
    }

    fun getDescriptionSize(): Flux<Int>{
        return getAllVideos().map { video -> video.description.length }
    }

    fun getAllVideosName(): Flux<String>{
        return getAllVideos().map { video -> video.name }
    }

    fun getVideosByRating(rate: Int): Flux<Video>{
        return getAllVideos().filter { video -> video.likes > rate }
    }

}