package io.github.steamfunc.expect

import io.github.steamfunc.expect.extension.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.slf4j.LoggerFactory
import java.io.IOException
import java.nio.file.NoSuchFileException
import kotlin.time.Duration.Companion.milliseconds

class BasicUsageTest {
    val log = LoggerFactory.getLogger(this.javaClass)

    @Test
    fun `it should fail when any exception did not occurred`() {
        assertThrows<AssertionError> {
            expect {
                // do nothing.
            }.throws<Exception>()
        }
    }

    @Test
    fun `it should verify exception type thrown in code block`() {
        expect {
            throw IOException()
        }.throws<Exception> {
            it.should.beInstanceOf<IOException>()
        }
    }

    @Test
    fun `it should verify exception type thrown in code block by expectThrows`() {
        expectThrows<IOException> {
            throw IOException()
        }
    }

    @Test
    fun `it should verify exception type thrown in code block by super class`() {
        expect {
            throw NoSuchFileException("/dummy")
        }.throws<IOException>()
    }

    @Test
    fun `it should verify wrong exception type thrown in code block`() {
        assertThrows<AssertionError> {
            expect {
                throw IOException()
            }.throws(NoSuchFileException::class)
        }
    }

    @Test
    fun `it should verify exception object`() {
        expect {
            throw IOException("fake exception")
        }.throws<IOException> {
            it.message.should.be("fake exception")
        }
    }

    @Test
    fun `it should use Exception class as default when given type`() {
        expect {
            throw Exception("hello")
        }.throws<Exception>()
    }

    @Test
    fun `it should verify given predicate using 'satisfy'`() {
        expect("hello").to.satisfy { length == 5 }
    }

    @Test
    fun `it should throw AssertionError when fail to test`() {
        expect {
            expect(3).to.satisfy { this < 0 }
        }.throws(AssertionError::class)
    }

    @Test
    fun `it should make you describe assertion message`() {
        expect {
            expect("thanks").to.satisfyThat("be a greeting") {
                arrayOf("hello", "aloha", "안녕")
                    .contains(it)
            }
        }.throws(AssertionError::class) {
            it.should.satisfy {
                log.debug("error: {}", this.message, this)
                message?.contains("should be a greeting") ?: false
            }
        }
    }

    @Test
    fun `expect(subj) {} could not use for null`() {
        expectThrows<AssertionError> {
            expect(null) {
                it.should.beNull()
            }
        }
    }

    @Test
    fun `test expect {} elapsedWithin`() {
        expect {
            Thread.sleep(10)
        }.elapsedWithin(10.milliseconds, 100.milliseconds)
    }

    @Test
    fun `failing test expect {} elapsedWithin`() {
        expect {
            expect {
                Thread.sleep(10)
            }.elapsedWithin(1.milliseconds, 9.milliseconds)
        }.throws<AssertionError>()
    }

    @Test
    fun `test expect {} throws elapsedWithin`() {
        expect {
            Thread.sleep(10)
            throw IOException()
        }.throws<IOException>()
            .elapsedWithin(10.milliseconds, 100.milliseconds)
    }

    @Test
    fun `test expect {} throws elapsedAtMost`() {
        expect {
            Thread.sleep(10)
            throw IOException()
        }.throws<IOException>()
            .elapsedAtMost(20.milliseconds)
    }

    @Test
    fun `failing test expect {} throws elapsedAtMost`() {
        expect {
            expect {
                Thread.sleep(10)
                throw IOException()
            }.throws<IOException>()
                .elapsedAtMost(5.milliseconds)
        }.throws<AssertionError>()
    }

    @Test
    fun `test expect { } elapsed without throws`() {
        expect {
            expect {
                throw IOException()
            }.elapsedAtMost(10.milliseconds)
        }.throws<AssertionError>()
    }

    @Test
    fun `it should test subject by given predicate`() {
        expect("hello").to.satisfy { length == 5 }
    }

    @Test
    fun `it should negative test for subject`() {
        expect(3).to.not.satisfy { this < 1 }
    }

    @Test
    fun `it should support block for multiple assertion`() {
        expect("hello") {
            it.should.not.beBlankOrNull()
            it.should.startWith("he")
            it.should.endWith("o")
        }
    }

}
