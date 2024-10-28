package com.hoz.project_reactor_examples.mock

import com.hoz.project_reactor_examples.Video

class MockVideo {

    fun generateVideos(): MutableList<Video>{
        return mutableListOf(
            Video("Video 1", "Description...", 8, 1001),
            Video("Video 2", "Description....", 7, 100),
            Video("Video 3", "Description...", 0, 1200),
            Video("Video 4", "Description.....", 2, 30),
            Video("Video 5", "Description........", 500, 10000),
            Video("Video 6", "Description...................", 35, 25),
            Video("Video 7", "Description.", 7, 758),
        )
    }

    fun generateVideos2(): MutableList<Video>{
        return mutableListOf(
            Video("Video 1", "Description...", 253, 58493),
            Video("Video 2", "Description...", 0, 194),
            Video("Video 3", "Description...", 8598, 15897051),
        )
    }

    fun generateVideos3(): MutableList<Video>{
        return mutableListOf(
            Video("Video 1", "Description...", 51328, 1325098324),
            Video("Video 2", "Description...", 204, null),
            Video("Video 3", "Description...", 301, 9004),
        )
    }

    fun generateVideos4(): MutableList<Video>{
        return mutableListOf(
            Video("Video", "Description...", 204, null),
        )
    }

}