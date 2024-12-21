package io.github.steamfunc.extension

import io.github.steamfunc.Expect

/**
 * Extension: Int
 *
 * @author Yunsang Choi
 */
public fun Expect<Int>.beGreaterThan(other: Int): Unit =
    satisfyThat("be greater than ${other.literal}") {
        it > other
    }

public fun Expect<Int>.beGreaterThanOrEqualTo(other: Int): Unit =
    satisfyThat("be greater than or equal to ${other.literal}") {
        it >= other
    }


public fun Expect<Int>.beLessThan(other: Int): Unit =
    satisfyThat("be less than ${other.literal}") {
        it < other
    }

public fun Expect<Int>.beLessThanOrEqualTo(other: Int): Unit =
    satisfyThat("be less than or equal to ${other.literal}") {
        it <= other
    }


public fun Expect<Int>.beBetween(lower: Int, upper: Int): Unit =
    satisfyThat("be between ${lower.literal} and ${upper.literal}") {
        it in lower..upper
    }

public fun Expect<Int>.beIn(range: ClosedRange<Int>): Unit =
    satisfyThat("be in the range of ${range.literal}") {
        it in range
    }


