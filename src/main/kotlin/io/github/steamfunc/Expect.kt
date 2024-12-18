package io.github.steamfunc

import io.github.steamfunc.policy.Stability
import org.slf4j.LoggerFactory

/**
 * Expect.
 *
 * It's extension point to define vocabulary for own your class.
 *
 * @author Yunsang Choi
 */
public class Expect<T : Any>
internal constructor(
    internal val subject: T?,
    internal val negative: Boolean = false,
) {
    private val verb: String = when (negative) {
        true -> "should not"
        false -> "should"
    }
    public val not: Expect<T> by lazy { Expect(subject, !negative) }
    private val log = LoggerFactory.getLogger(this.javaClass)


    /**
     * test given predicate.
     *
     * if subject is null, it will throw assertError.
     */
    public fun satisfy(predicate: T.() -> Boolean) {
        satisfyThatForNullable("satisfy given predicate <$predicate>") {
            it?.predicate() == true
        }
    }

    /**
     * assert with assertion message and predicate.
     *
     * use it to define your expect vocabulary.
     */
    public fun satisfyThat(description: String, predicate: (T) -> Boolean) {
        satisfyThatForNullable(description) { subj ->
            subj?.let { predicate(it) } == true
        }
    }

    /**
     * same as `satisfyThat`.
     * but it could be test nullable.
     */
    public fun satisfyThatForNullable(description: String, predicate: (T?) -> Boolean) {
        initTestProp()
        if (predicate(subject) == negative) {
            log.debug("$subjectAsText $verb $description : FAIL")
            throw AssertionError(errorMessage(description))
        } else {
            log.debug("$subjectAsText $verb $description : OK")
        }
    }

    private fun errorMessage(description: String): String {
        return "It $verb $description, but it was <$subjectAsText>."
    }


    //--------------------------------
    // property of subject to test
    //--------------------------------
    private var testProp: Prop? = null

    private inner class Prop(val name: String, val value: Any?) {
        val asText: String by lazy { "$name=${value.literal}" }
    }

    @Stability.Experimental
    public fun <X : Any?> X.asTestProp(name: String): X {
        testProp = Prop(name, this)
        return this
    }

    private fun initTestProp() {
        testProp = null
    }

    private val subjectAsText: String
        get() {
            return if (testProp == null) {
                subject.literal
            } else {
                "${subject.literal}(${testProp!!.asText})"
            }
        }

    // Expect class scoped extension (for print object in assertion message)
    public val <X : Any?> X.literal: String
        get() = Literals.literal(this)

    @Suppress("POTENTIALLY_NON_REPORTED_ANNOTATION")
    @Deprecated("DO NOT USE")
    override fun equals(other: Any?): Boolean {
        throw RuntimeException("DO NOT USE THIS METHOD")
    }

    @Suppress("POTENTIALLY_NON_REPORTED_ANNOTATION")
    @Deprecated("DO NOT USE")
    override fun hashCode(): Int {
        throw RuntimeException("DO NOT USE THIS METHOD")
    }

    @Suppress("POTENTIALLY_NON_REPORTED_ANNOTATION")
    @Deprecated("DO NOT USE")
    override fun toString(): String {
        throw RuntimeException("DO NOT USE THIS METHOD")
    }
}
