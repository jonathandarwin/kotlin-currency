package com.jonathandarwin.domain.util

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

/**
 * Created By : Jonathan Darwin on October 24, 2022
 */
fun CoroutineScope.launchWithCatch(
    context: CoroutineContext = coroutineContext,
    start: CoroutineStart = CoroutineStart.DEFAULT,
    block: suspend CoroutineScope.() -> Unit,
    catch: suspend (Throwable) -> Unit
) {
    launch(context, start) {
        try {
            block()
        } catch (e: Exception) {
            catch(e)
        }
    }
}