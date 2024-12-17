package io.github.steamfunc

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.time.Duration
import java.time.Instant
import kotlin.reflect.KClass
import kotlin.time.toJavaDuration

/**
 * Expectation of error.
 *
 * it's catch given block and test the exception.
 *
 * @author Yunsang Choi
 */
class ErrorExpectation
internal constructor(block: () -> Unit) {
    private val log: Logger = LoggerFactory.getLogger(this.javaClass)
    private val startedAt = Instant.now()
    private var finishedAt: Instant

    // execute and catch
    private val thrown: Throwable? = try {
        block()
        null
    } catch (e: Throwable) {
        e
    } finally {
        finishedAt = Instant.now()
    }

    private val elapsedTime = lazy { Duration.between(startedAt, finishedAt) }

    fun elapsedWithin(lower: Duration, upper: Duration) = apply {
        require(lower <= upper) { "lower bound should be less than or equal to upper bound" }
        if (elapsedTime.value !in lower..upper) {
            log.debug("elapsed time is {} but expected range is {} ~ {} : FAIL", elapsedTime.value, lower, upper)
            throw AssertionError("elapsed time is ${elapsedTime.value} but expected range is $lower ~ $upper")
        }
    }

    fun elapsedWithin(range: ClosedRange<Duration>) = apply {
        elapsedWithin(range.start, range.endInclusive)
    }

    @JvmName("elapsedWithinKotlinDuration")
    fun elapsedWithin(lower: kotlin.time.Duration, upper: kotlin.time.Duration) = apply {
        elapsedWithin(lower.toJavaDuration(), upper.toJavaDuration())
    }

    @JvmName("elapsedWithinKotlinDuration")
    fun elapsedWithin(range: ClosedRange<kotlin.time.Duration>) = apply {
        elapsedWithin(range.start.toJavaDuration(), range.endInclusive.toJavaDuration())
    }

    /**
     * Test type of exception.
     *
     * Caught exception should be instance of given expection class.
     * if not, it will throw AssertionError.
     *
     */
    fun <T : Throwable> throws(
        exceptionClass: KClass<out T>,
        clause: (T) -> Unit = {},
    ) = apply {
        if (thrown == null) {
            log.debug("No exception had been thrown : FAIL")
            throw AssertionError("expected to occur a exception<$exceptionClass> but no exception was thrown.")
        }
        if (!exceptionClass.isInstance(thrown)) {
            log.debug("{} has been thrown, but expected <{}> : FAIL", thrown.literal, exceptionClass)
            throw AssertionError("expected <$exceptionClass> to be thrown, but <${thrown::class}> was thrown.", thrown)
        }
        log.debug("{} has been thrown (expected:<{}>) : OK", thrown.literal, exceptionClass)
        @Suppress("UNCHECKED_CAST")
        clause(thrown as T)
    }


    /**
     * reified version.
     */
    @JvmName("reifiedThrows")
    inline fun <reified T : Throwable> throws(noinline clause: (T) -> Unit = {}) = apply {
        throws(T::class, clause)
    }

    /**
     * short-cut method.
     *
     */
    fun throws(clause: (Exception) -> Unit = {}) = apply{
        throws(Exception::class, clause)
    }

    // Expect class scoped extension (for print object in assertion message)
    internal val <X : Any?> X.literal: String
        get() = Literalizer.literal(this)
}
