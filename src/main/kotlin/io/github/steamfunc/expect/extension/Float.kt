package io.github.steamfunc.expect.extension

import io.github.steamfunc.expect.Expect

/**
 * Extension: Float
 *
 * @author Yunsang Choi
 */
public fun Expect<Float>.beGreaterThan(other: Float): Unit =
    satisfyThat("be greater than ${other.literal}") {
        it > other
    }

public fun Expect<Float>.beGreaterThanOrEqualTo(other: Float): Unit =
    satisfyThat("be greater than or equal to ${other.literal}") {
        it >= other
    }


public fun Expect<Float>.beLessThan(other: Float): Unit =
    satisfyThat("be less than ${other.literal}") {
        it < other
    }

public fun Expect<Float>.beLessThanOrEqualTo(other: Float): Unit =
    satisfyThat("be less than or equal to ${other.literal}") {
        it <= other
    }


public fun Expect<Float>.beBetween(lower: Float, upper: Float): Unit =
    satisfyThat("be between ${lower.literal} and ${upper.literal}") {
        it in lower..upper
    }

public fun Expect<Float>.beIn(range: ClosedRange<Float>): Unit =
    satisfyThat("be in the range of ${range.literal}") {
        it in range
    }

public fun Expect<Float>.equalToWithin(value: Float, error: Double): Unit =
    satisfyThat("equal to ${value.literal} within ±${error.literal}") {
        Math.abs(it - value) <= error
    }
