package base

import kotlinx.coroutines.*

fun main() = runBlocking { // start main coroutine
    val job = GlobalScope.launch { // launch a new coroutine in background and continue
        delay(1000L)
        println("World!")
    }
    println("Hello,") // main coroutine continues here immediately
    job.join()
}