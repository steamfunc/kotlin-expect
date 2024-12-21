package io.github.steamfunc.extension

import io.github.steamfunc.Expect
import java.time.Instant
import java.time.temporal.TemporalAmount
import java.util.*

/**
 * Extension : Instant
 *
 * @author Yunsang Choi
 */

public fun Expect<Instant>.beBefore(other: Instant): Unit =
    satisfyThat("be before ${other.literal}") {
        it.isBefore(other)
    }

public fun Expect<Instant>.beBefore(other: Date): Unit =
    satisfyThat("be before ${other.literal}") {
        it.isBefore(other.toInstant())
    }

public fun Expect<Instant>.beAfter(other: Instant): Unit =
    satisfyThat("be after ${other.literal}") {
        it.isAfter(other)
    }

public fun Expect<Instant>.beAfter(other: Date): Unit =
    satisfyThat("be after ${other.literal}") {
        it.isAfter(other.toInstant())
    }

public fun Expect<Instant>.beIn(range: ClosedRange<Instant>): Unit =
    satisfyThat("be in the range of ${range.literal}") {
        it in range
    }

public fun Expect<Instant>.beApproximatedTo(other: Instant, marginOfError: TemporalAmount): Unit =
    satisfyThat("be approximated to ${other.literal} within ${marginOfError.literal}") {
        it in (other.minus(marginOfError)..other.plus(marginOfError))
    }

