package com.example.notesapp.util

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

object Coroutines {
    //This method allows to first perform the first coroutine task in the IO thread
    //And then perform the second callback in Main thread
    fun<T: Any> ioThenMain(work: suspend (() -> T?), callback: ((T?)->Unit)) =
        CoroutineScope(Dispatchers.Main).launch {
            val data = CoroutineScope(Dispatchers.IO).async  rt@{
                return@rt work()
            }.await() //on IO thread

            callback(data) //on Main thread
        }

}