package com.hoz.project_reactor_examples

import org.reactivestreams.Subscriber
import org.reactivestreams.Subscription

data class User(
    private var name: String
): Subscriber<Video> {

    override fun onSubscribe(subscription: Subscription) {
        println("onSubscribe, $name")
        subscription.request(Long.MAX_VALUE)
    }
    override fun onNext(video: Video) {
        println("onNext, $video")
    }

    override fun onError(error: Throwable?) {
        println("onError, $error")
    }

    override fun onComplete() {
        println("onComplete")
    }

}