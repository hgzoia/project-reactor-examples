package com.hoz.project_reactor_examples

import reactor.core.publisher.Mono

data class Video(
    var name: String,
    var description: String,
    var likes: Int,
    var views: Int?
){
    fun like(): Mono<Video> {
        this.likes++
        return Mono.just(this)
    }
}