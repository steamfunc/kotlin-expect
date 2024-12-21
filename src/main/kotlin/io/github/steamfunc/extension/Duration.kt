package io.github.steamfunc.extension

import io.github.steamfunc.Expect
import java.time.Duration
import kotlin.math.abs

/**
 * Extension: Double
 *
 * @author Yunsang Choi
 */


public fun Expect<Duration>.beGreaterThan(other: Duration): Unit =
    satisfyThat("be greater than ${other.literal}") {
        it > other
    }

public fun Expect<Duration>.beGreaterThanOrEqualTo(other: Duration): Unit =
    satisfyThat("be greater than or equal to ${other.literal}") {
        it >= other
    }

public fun Expect<Duration>.beLessThan(other: Duration): Unit =
    satisfyThat("be less than ${other.literal}") {
        it < other
    }

public fun Expect<Duration>.beLessThanOrEqualTo(other: Duration): Unit =
    satisfyThat("be less than or equal to ${other.literal}") {
        it <= other
    }

public fun Expect<Duration>.beBetween(lower: Duration, upper: Duration): Unit =
    satisfyThat("be between ${lower.literal} and ${upper.literal}") {
        it in lower..upper
    }

public fun Expect<Duration>.beIn(range: ClosedRange<Duration>): Unit =
    satisfyThat("be in the range of ${range.literal}") {
        it in range
    }


public fun Expect<Duration>.equalToWithin(value: Duration, error: Duration): Unit =
    satisfyThat("equal to ${value.literal} within ±${error.literal}") {
        abs(it.toNanos() - value.toNanos()) <= error.toNanos()
    }


