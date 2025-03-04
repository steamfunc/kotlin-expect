package io.github.steamfunc.expect.extension

import io.github.steamfunc.expect.Expect

/**
 * Extension : Boolean
 * @author Yunsang Choi
 */

public fun Expect<Boolean>.beTrue(): Unit =
    satisfyThat("be true") {
        it
    }

public fun Expect<Boolean>.beFalse(): Unit =
    satisfyThat("be false") {
        !it
    }
