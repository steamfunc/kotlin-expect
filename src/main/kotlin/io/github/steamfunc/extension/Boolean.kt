package io.github.steamfunc.extension

import io.github.steamfunc.Expect

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
