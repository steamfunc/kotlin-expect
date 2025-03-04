package io.github.steamfunc.expect.extension

import io.github.steamfunc.expect.Expect

/**
 * Extension : String
 *
 * @author Yunsang Choi
 */

public fun Expect<String>.startWith(prefix: CharSequence, ignoreCase: Boolean = false): Unit =
    satisfyThat("start with ${prefix.literal} (ignoreCase:$ignoreCase)") {
        it.startsWith(prefix, ignoreCase)
    }

public fun Expect<String>.startWith(prefix: Char, ignoreCase: Boolean = false): Unit =
    satisfyThat("start with ${prefix.literal} (ignoreCase:$ignoreCase)") {
        it.startsWith(prefix, ignoreCase)
    }

public fun Expect<String>.endWith(suffix: CharSequence, ignoreCase: Boolean = false): Unit =
    satisfyThat("end with ${suffix.literal} (ignoreCase:$ignoreCase)") {
        it.endsWith(suffix, ignoreCase)
    }

public fun Expect<String>.endWith(suffix: Char, ignoreCase: Boolean = false): Unit =
    satisfyThat("end with ${suffix.literal} (ignoreCase:$ignoreCase)") {
        it.endsWith(suffix, ignoreCase)
    }

public fun Expect<String>.beEmpty(): Unit =
    satisfyThat("be empty") {
        it.isEmpty()
    }

public fun Expect<String>.beEmptyOrNull(): Unit =
    satisfyThatForNullable("be empty or null") {
        it == null || it.isEmpty()
    }

public fun Expect<String>.beBlank(): Unit =
    satisfyThat("be blank") {
        it.isBlank()
    }

public fun Expect<String>.beBlankOrNull(): Unit =
    satisfyThatForNullable("be blank or null") {
        it == null || it.isBlank()
    }

public fun Expect<String>.containChar(char: Char, ignoreCase: Boolean = false): Unit =
    satisfyThat("contain ${char.literal}") {
        it.contains(char, ignoreCase)
    }

public fun Expect<String>.containString(substr: CharSequence, ignoreCase: Boolean = false): Unit =
    satisfyThat("contain ${substr.literal}") {
        it.contains(substr, ignoreCase)
    }

public fun Expect<String>.match(regex: Regex): Unit =
    satisfyThat("match ${regex.literal}") {
        it.matches(regex)
    }

public fun Expect<String>.match(regex: String): Unit = match(Regex(regex))

public fun Expect<String>.haveLengthOf(length: Int): Unit =
    satisfyThat("have length of ${length.literal}") {
        it.length.asTestProp("length") == length
    }

public fun Expect<String>.haveLengthIn(range: IntRange): Unit =
    satisfyThat("have length in the range $range") {
        it.length.asTestProp("length") in range
    }
