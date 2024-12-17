package io.github.steamfunc.extension

import io.github.steamfunc.Expect

/**
 * Extension : Boolean
 * @author Yunsang Choi
 */

fun Expect<Boolean>.beTrue() =
        satisfyThat("be true") {
            it
        }

fun Expect<Boolean>.beFalse() =
        satisfyThat("be false") {
            !it
        }
