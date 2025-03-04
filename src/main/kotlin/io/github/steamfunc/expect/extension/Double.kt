package io.github.steamfunc.expect.extension

import io.github.steamfunc.expect.Expect
import kotlin.math.abs

/**
 * Extension: Double
 *
 * @author Yunsang Choi
 */
public fun Expect<Double>.beGreaterThan(other: Double): Unit =
    satisfyThat("be greater than ${other.literal}") {
        it > other
    }

public fun Expect<Double>.beGreaterThanOrEqualTo(other: Double): Unit =
    satisfyThat("be greater than or equal to ${other.literal}") {
        it >= other
    }


public fun Expect<Double>.beLessThan(other: Double): Unit =
    satisfyThat("be less than ${other.literal}") {
        it < other
    }

public fun Expect<Double>.beLessThanOrEqualTo(other: Double): Unit =
    satisfyThat("be less than or equal to ${other.literal}") {
        it <= other
    }


public fun Expect<Double>.beBetween(lower: Double, upper: Double): Unit =
    satisfyThat("be between ${lower.literal} and ${upper.literal}") {
        it in lower..upper
    }

public fun Expect<Double>.beIn(range: ClosedRange<Double>): Unit =
    satisfyThat("be in the range of ${range.literal}") {
        it in range
    }


public fun Expect<Double>.equalToWithin(value: Double, error: Double): Unit =
    satisfyThat("equal to ${value.literal} within ±${error.literal}") {
        abs(it - value) <= error
    }


