package com.hoz.project_reactor_examples

import org.springframework.test.context.ContextConfiguration
import reactor.core.publisher.Flux
import reactor.core.publisher.FluxSink
import reactor.test.StepVerifier
import java.io.BufferedReader
import java.io.FileReader
import java.io.IOException
import java.util.concurrent.CompletableFuture
import kotlin.test.Test

@ContextConfiguration
class CreateOperatorTest {

    @Test
    fun testFileReadingCreate(){

        val filePath = "C:\\Users\\hugoo\\Documents\\estudos\\projetos\\project-reactor-examples\\src\\test\\resources\\example.txt"
        val filePath2 = "C:\\Users\\hugoo\\Documents\\estudos\\projetos\\project-reactor-examples\\src\\test\\resources\\example2.txt"

        val fileFlux: Flux<String?> = Flux.create { emitter ->

            val task1 = CompletableFuture.runAsync { readFileFluxSink(emitter, filePath) }
            val task2 = CompletableFuture.runAsync { readFileFluxSink(emitter, filePath2) }
            CompletableFuture.allOf(task1, task2).join()

            emitter.complete()
        }.log()

        StepVerifier.create(fileFlux)
            .expectNextCount(6)
            .expectComplete()
            .verify()
    }

    private fun readFileFluxSink(emitter: FluxSink<String>, filePath: String){
        try {
            BufferedReader(FileReader(filePath)).use { reader ->
                var line: String?
                while (reader.readLine().also { line = it } != null) {
                    emitter.next(line!!)
                }
            }
        } catch (ex: IOException) {
            emitter.error(ex)
        }
    }
}