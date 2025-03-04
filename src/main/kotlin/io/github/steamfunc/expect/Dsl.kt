package io.github.steamfunc.expect

import io.github.steamfunc.expect.policy.Stability
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract

/**
 * DSL entry points.
 *
 * @author Yunsang Choi
 */

/**
 * expect exception while executing code block
 */
@Stability.Stable
public fun expect(block: () -> Unit): BlockExpectation {
    return BlockExpectation(block)
}

/**
 * expect exception while executing code block (another version)
 */
@Stability.Stable
@Suppress("WRONG_INVOCATION_KIND", "LEAKED_IN_PLACE_LAMBDA")
@OptIn(ExperimentalContracts::class)
public inline fun <reified T : Throwable> expectThrows(noinline block: () -> Unit) {
    contract {
        callsInPlace(block, InvocationKind.EXACTLY_ONCE)
    }
    expect(block).throws<T>()
}

/**
 * expect subject to...
 */
@Stability.Stable
public fun <T : Any> expect(subject: T?): Expectation<T> = Expectation(subject)

/**
 * expect subject that ...
 */
@Stability.Stable
@OptIn(ExperimentalContracts::class)
public fun <T : Any> expect(subject: T?, clause: (T) -> Unit) {
    contract {
        returns() implies (subject != null)
        callsInPlace(clause, InvocationKind.EXACTLY_ONCE)
    }
    if (subject == null) {
        throw AssertionError("Cannot execute expect clause for null.")
    }
    clause(subject)
}

/**
 * rspec old-style : `should`
 *
 */
@Stability.Stable
public val <T : Any> T?.should: Expect<T>
    get() = Expect(this)

