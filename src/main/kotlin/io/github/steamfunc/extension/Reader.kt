package io.github.steamfunc.extension

import io.github.steamfunc.Expect
import java.io.Reader

/**
 * Extension: Reader
 *
 * @author Yunsang Choi
 */
public fun <T : Reader> Expect<T>.equalAsText(text: String, ignoreCase: Boolean = false): Unit =
    satisfyThat("equal to ${text.literal} as text") {
        it.readText().asTestProp("content").equals(text, ignoreCase)
    }

public fun <T : Reader> Expect<T>.contain(text: String, ignoreCase: Boolean = false): Unit =
    satisfyThat("contain ${text.literal}") {
        it.readText().asTestProp("content").contains(text, ignoreCase)
    }