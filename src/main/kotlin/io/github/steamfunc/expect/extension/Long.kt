package io.github.steamfunc.expect.extension

import io.github.steamfunc.expect.Expect

/**
 * Extension: Long
 *
 * @author Yunsang Choi
 */
public fun Expect<Long>.beGreaterThan(other: Long): Unit =
    satisfyThat("be greater than ${other.literal}") {
        it > other
    }

public fun Expect<Long>.beGreaterThanOrEqualTo(other: Long): Unit =
    satisfyThat("be greater than or equal to ${other.literal}") {
        it >= other
    }


public fun Expect<Long>.beLessThan(other: Long): Unit =
    satisfyThat("be less than ${other.literal}") {
        it < other
    }

public fun Expect<Long>.beLessThanOrEqualTo(other: Long): Unit =
    satisfyThat("be less than or equal to ${other.literal}") {
        it <= other
    }


public fun Expect<Long>.beBetween(lower: Long, upper: Long): Unit =
    satisfyThat("be between ${lower.literal} and ${upper.literal}") {
        it in lower..upper
    }

public fun Expect<Long>.beIn(range: ClosedRange<Long>): Unit =
    satisfyThat("be in the range of ${range.literal}") {
        it in range
    }


