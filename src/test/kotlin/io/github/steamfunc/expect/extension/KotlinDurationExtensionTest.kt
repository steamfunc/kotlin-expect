package io.github.steamfunc.expect.extension

import io.github.steamfunc.expect.should
import org.junit.jupiter.api.Test
import kotlin.time.Duration.Companion.hours
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.Duration.Companion.minutes
import kotlin.time.Duration.Companion.seconds

class KotlinDurationExtensionTest {

    @Test
    fun `test beGreaterThan`() {
        2.seconds.should.beGreaterThan(1.seconds)
        1.minutes.should.beGreaterThan(59.seconds)
        1.hours.should.not.beGreaterThan(60.minutes)
    }

    @Test
    fun `test beGreaterThanOrEqualTo`() {
        1.seconds.should.beGreaterThanOrEqualTo(1.seconds)
        1.minutes.should.beGreaterThanOrEqualTo(59.seconds)
    }

    @Test
    fun `test beLessThan`() {
        59.seconds.should.beLessThan(1.minutes)
        60.seconds.should.not.beLessThan(1.minutes)
        1.minutes.should.not.beLessThan(59.seconds)
    }

    @Test
    fun `test beLessThanOrEqualTo`() {
        59.seconds.should.beLessThanOrEqualTo(1.minutes)
        60.seconds.should.beLessThanOrEqualTo(1.minutes)
        1.minutes.should.beLessThanOrEqualTo(60.seconds)
    }

    @Test
    fun `test beBetween`() {
        2.minutes.should.beBetween(90.seconds, 2.minutes)
        2.minutes.should.beBetween(2.minutes, 121.seconds)
        1.minutes.should.not.beBetween(61.seconds, 120.seconds)
    }

    @Test
    fun `test beIn the range`() {
        2.minutes.should.beIn(90.seconds..2.minutes)
        2.minutes.should.beIn(2.minutes..121.seconds)
        1.minutes.should.not.beIn(61.seconds..120.seconds)
    }


    @Test
    fun `test equalToWithin`() {
        59.seconds.should.equalToWithin(1.minutes, error = 1.seconds)
        59.seconds.should.not.equalToWithin(1.minutes, error = 100.milliseconds)
    }
}