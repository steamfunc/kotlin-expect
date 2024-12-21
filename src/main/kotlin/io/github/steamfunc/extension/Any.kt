package io.github.steamfunc.extension

import io.github.steamfunc.Expect
import kotlin.reflect.KClass

/**
 * Extension : Any
 *
 * @author Yunsang Choi
 */

public infix fun <T : Any> Expect<T>.beSameInstance(value: Any?): Unit =
    satisfyThatForNullable("be same instance of <${value.literal}>") {
        it === value
    }

public infix fun <T : Any> Expect<T>.be(value: T?): Unit =
    satisfyThatForNullable("be <${value.literal}>") {
        it == value
    }

public infix fun <T : Any> Expect<T>.equal(value: Any?): Unit =
    satisfyThatForNullable("equal <${value.literal}>") {
        it == value
    }

public fun <T : Any> Expect<T>.beNull() {
    satisfyThatForNullable("be null") {
        it == null
    }
}

public infix fun <T : Any> Expect<T>.beInstanceOf(type: KClass<*>): Unit =
    satisfyThatForNullable("be instance of <${type.literal}>") {
        type.isInstance(it)
    }

public inline fun <reified S : Any> Expect<*>.beInstanceOf(): Unit =
    satisfyThatForNullable("be instance of <${S::class.literal}>") {
        S::class.isInstance(it)
    }
