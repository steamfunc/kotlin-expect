package io.github.steamfunc.extension

import io.github.steamfunc.expect
import io.github.steamfunc.should
import org.junit.jupiter.api.Test

/**
 *
 * @author Yunsang Choi
 */

class BooleanExtensionTest {
    @Test
    fun `test true`() {
        expect(true) {
            it.should.beTrue()
            it.should.not.beFalse()
        }
    }

    @Test
    fun `test false`() {
        expect(false) {
            it.should.beFalse()
            it.should.not.beTrue()
        }
        expect {
            true.should.beFalse()
        }.throws(AssertionError::class)
    }
}
