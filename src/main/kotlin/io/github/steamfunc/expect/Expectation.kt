package io.github.steamfunc.expect

/**
 * Expectation.
 *
 * @author Yunsang Choi
 */
public class Expectation<T : Any>
internal constructor(subject: T?) {
    public val to: Expect<T> by lazy { Expect(subject) }
}

