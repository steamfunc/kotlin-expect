package io.github.steamfunc.extension

import io.github.steamfunc.Expect

/**
 * Extension : String
 *
 * @author Yunsang Choi
 */


fun Expect<String>.startWith(prefix: CharSequence, ignoreCase: Boolean = false) =
        satisfyThat("start with ${prefix.literal} (ignoreCase:$ignoreCase)") {
            it.startsWith(prefix, ignoreCase)
        }

fun Expect<String>.startWith(prefix: Char, ignoreCase: Boolean = false) =
        satisfyThat("start with ${prefix.literal} (ignoreCase:$ignoreCase)") {
            it.startsWith(prefix, ignoreCase)
        }

fun Expect<String>.endWith(suffix: CharSequence, ignoreCase: Boolean = false) =
        satisfyThat("end with ${suffix.literal} (ignoreCase:$ignoreCase)") {
            it.endsWith(suffix, ignoreCase)
        }

fun Expect<String>.endWith(suffix: Char, ignoreCase: Boolean = false) =
        satisfyThat("end with ${suffix.literal} (ignoreCase:$ignoreCase)") {
            it.endsWith(suffix, ignoreCase)
        }

fun Expect<String>.beEmpty() =
        satisfyThat("be empty") {
            it.isEmpty()
        }

fun Expect<String>.beEmptyOrNull() =
        satisfyThatForNullable("be empty") {
            it == null || it.isEmpty()
        }

fun Expect<String>.beBlank() =
        satisfyThat("be empty") {
            it.isBlank()
        }

fun Expect<String>.beBlankOrNull() =
        satisfyThatForNullable("be empty") {
            it == null || it.isBlank()
        }

fun Expect<String>.containChar(char: Char, ignoreCase: Boolean = false) =
        satisfyThat("contain ${char.literal}") {
            it.contains(char, ignoreCase)
        }

fun Expect<String>.containString(substr: CharSequence, ignoreCase: Boolean = false) =
        satisfyThat("contain ${substr.literal}") {
            it.contains(substr, ignoreCase)
        }

fun Expect<String>.match(regex: Regex) =
        satisfyThat("match ${regex.literal}") {
            it.matches(regex)
        }

fun Expect<String>.match(regex: String) = match(Regex(regex))

fun Expect<String>.haveLengthOf(length: Int) =
        satisfyThat("have length of ${length.literal}") {
            it.length.asTestProp("length") == length
        }

fun Expect<String>.haveLengthIn(range: IntRange) =
        satisfyThat("have length in the range $range") {
            it.length.asTestProp("length") in range
        }
