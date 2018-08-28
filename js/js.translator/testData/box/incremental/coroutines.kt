// IGNORE_BACKEND: JS_IR
// EXPECTED_REACHABLE_NODES: 1172
// FILE: a.kt
// WITH_RUNTIME
import kotlin.coroutines.*
import kotlin.coroutines.intrinsics.*

suspend fun suspendThere(v: String): String = suspendCoroutineOrReturn { x ->
    x.resume(v)
    COROUTINE_SUSPENDED
}

suspend fun suspendHere(): String = suspendThere("O") + suspendThere("K")

// FILE: b.kt
// RECOMPILE
import kotlin.coroutines.experimental.*
import kotlin.coroutines.experimental.intrinsics.*

fun builder(c: suspend () -> Unit) {
    c.startCoroutine(object : Continuation<Unit> {
        override val context = EmptyCoroutineContext

        override fun resumeWith(result: SuccessOrFailure<Unit>) {}
    })
}

fun box(): String {
    var result = ""

    builder {
        result = suspendHere()
    }

    return result
}
