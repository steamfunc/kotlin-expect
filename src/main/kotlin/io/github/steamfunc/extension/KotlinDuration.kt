package io.github.steamfunc.extension

import io.github.steamfunc.Expect
import kotlin.math.abs
import kotlin.time.Duration
import kotlin.time.ExperimentalTime


/**
 * Extension: Double
 *
 * @author Yunsang Choi
 */


@OptIn(ExperimentalTime::class)
fun Expect<Duration>.beGreaterThan(other: Duration) =
    satisfyThat("be greater than ${other.literal}") {
        it > other
    }

@OptIn(ExperimentalTime::class)
fun Expect<Duration>.beGreaterThanOrEqualTo(other: Duration) =
    satisfyThat("be greater than or equal to ${other.literal}") {
        it >= other
    }

@OptIn(ExperimentalTime::class)
fun Expect<Duration>.beLessThan(other: Duration) =
    satisfyThat("be less than ${other.literal}") {
        it < other
    }

@OptIn(ExperimentalTime::class)
fun Expect<Duration>.beLessThanOrEqualTo(other: Duration) =
    satisfyThat("be less than or equal to ${other.literal}") {
        it <= other
    }

@OptIn(ExperimentalTime::class)
fun Expect<Duration>.beBetween(lower: Duration, upper: Duration) =
    satisfyThat("be between ${lower.literal} and ${upper.literal}") {
        it in lower..upper
    }

@OptIn(ExperimentalTime::class)
fun Expect<Duration>.beIn(range: ClosedRange<Duration>) =
    satisfyThat("be in the range of ${range.literal}") {
        it in range
    }

@OptIn(ExperimentalTime::class)
fun Expect<Duration>.equalToWithin(value: Duration, error: Duration) =
    satisfyThat("equal to ${value.literal} within ±${error.literal}") {
        abs(it.inWholeNanoseconds - value.inWholeNanoseconds) <= error.inWholeNanoseconds
    }