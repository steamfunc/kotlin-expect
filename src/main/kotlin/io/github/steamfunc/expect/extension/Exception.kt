package io.github.steamfunc.expect.extension

import io.github.steamfunc.expect.Expect
import kotlin.reflect.KClass

/**
 *  Extension: Throwable
 *
 * @author Yunsang Choi
 */

public fun <T : Throwable> Expect<T>.haveMessage(expectMessage: String?): Unit =
    satisfyThat("has message of ${expectMessage.literal}") {
        it.message.asTestProp("message") == expectMessage
    }

public fun <T : Throwable> Expect<T>.haveNoMessage(): Unit =
    satisfyThat("has no message") {
        it.message.asTestProp("message") == null
    }

public fun <T : Throwable, C : Throwable> Expect<T>.haveCause(cause: KClass<C>): Unit =
    satisfyThat("has cause of ${cause.literal}") {
        it.cause.asTestProp("cause")
        sequence {
            yield(it)
            var current = it.cause
            while (current != null) {
                yield(current)
                current = current.cause
            }
        }.any { cause.java.isAssignableFrom(it::class.java) }
    }
