package io.github.steamfunc.expect.extension

import io.github.steamfunc.expect.Expect
import java.io.InputStream
import java.nio.charset.Charset

/**
 * Extension : InputStream
 *
 * @author Yunsang Choi
 */

public fun <T : InputStream> Expect<T>.equalAsText(
    text: String,
    ignoreCase: Boolean = false,
    charset: Charset = Charsets.UTF_8
): Unit = satisfyThat("equal to ${text.literal} as text") {
    it.reader(charset).readText().asTestProp("content").equals(text, ignoreCase)
}

public fun <T : InputStream> Expect<T>.contain(
    text: String,
    ignoreCase: Boolean = false,
    charset: Charset = Charsets.UTF_8
): Unit = satisfyThat("contain ${text.literal}") {
    it.reader(charset).readText().asTestProp("content").contains(text, ignoreCase)
}

