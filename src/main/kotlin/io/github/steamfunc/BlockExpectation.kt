package io.github.steamfunc

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.time.Duration
import java.time.Instant
import kotlin.reflect.KClass
import kotlin.time.toJavaDuration

/**
 * Expectation of lambda block.
 *
 * it's catch given block and test the exception.
 *
 * @author Yunsang Choi
 */
public class BlockExpectation
internal constructor(block: () -> Unit) {
    private val log: Logger = LoggerFactory.getLogger(this.javaClass)
    private val startedAt = Instant.now()
    private val finishedAt: Instant

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

    public fun elapsedWithin(lower: Duration, upper: Duration): BlockExpectation = apply {
        require(lower <= upper) { "lower bound should be less than or equal to upper bound" }
        if (elapsedTime.value !in lower..upper) {
            log.debug("elapsed time is {} but expected range is {} ~ {} : FAIL", elapsedTime.value, lower, upper)
            throw AssertionError("elapsed time is ${elapsedTime.value} but expected range is $lower ~ $upper")
        }
    }

    public fun elapsedWithin(range: ClosedRange<Duration>): BlockExpectation = apply {
        elapsedWithin(range.start, range.endInclusive)
    }

    @JvmName("elapsedWithinKotlinDuration")
    public fun elapsedWithin(lower: kotlin.time.Duration, upper: kotlin.time.Duration): BlockExpectation = apply {
        elapsedWithin(lower.toJavaDuration(), upper.toJavaDuration())
    }

    @JvmName("elapsedWithinKotlinDuration")
    public fun elapsedWithin(range: ClosedRange<kotlin.time.Duration>): BlockExpectation = apply {
        elapsedWithin(range.start.toJavaDuration(), range.endInclusive.toJavaDuration())
    }

    /**
     * Test type of exception.
     *
     * Caught exception should be instance of given expection class.
     * if not, it will throw AssertionError.
     *
     */
    public fun <T : Throwable> throws(
        exceptionClass: KClass<out T>,
        clause: (T) -> Unit = {},
    ): BlockExpectation = apply {
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
    public inline fun <reified T : Throwable> throws(noinline clause: (T) -> Unit = {}): BlockExpectation = apply {
        throws(T::class, clause)
    }

    /**
     * short-cut method.
     *
     */
    public fun throws(clause: (Exception) -> Unit = {}): BlockExpectation = apply {
        throws(Exception::class, clause)
    }

    // Expect class scoped extension (for print object in assertion message)
    internal val <X : Any?> X.literal: String
        get() = Literalizer.literal(this)
}
