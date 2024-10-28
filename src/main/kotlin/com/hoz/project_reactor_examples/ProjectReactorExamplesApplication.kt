package com.hoz.project_reactor_examples

fun main() {
	subscriberTest()
}

fun subscriberTest(){
	val publisher = YoutubeChannel(mutableListOf())
	publisher.addVideo(Video("Reactive Programming",
		"Description...", 10, 200))

	publisher.addVideo(Video("Java Programming",
		"Description...", 20, 10000))

	val user = User("Hugo")
	publisher.getAllVideos().subscribeWith(user)
}