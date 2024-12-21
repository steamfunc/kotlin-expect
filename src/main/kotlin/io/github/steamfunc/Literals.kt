package io.github.steamfunc

import java.time.temporal.Temporal
import java.util.*
import kotlin.reflect.KClass
import kotlin.time.Duration

/**
 * Literals.
 *
 * This object provides functionality to convert an object into a string representation for use in an expect message.
 *
 * If the object is an implementation of the StringRepresentable interface, it uses StringRepresentable.toLiteral().
 * For some basic Java/Kotlin classes, it provides a literal string representation that differs from toString().
 * If neither case applies, it returns the value of the toString() method.
 *
 * @author Yunsang Choi
 */
internal object Literals {
    /**
     * Convert an object to a string literal.
     */
    fun <T : Any> literal(value: T?): String {
        if (value is StringRepresentable) {
            return value.toLiteral()
        }
        @Suppress("UNCHECKED_CAST")
        return value?.let {
            converters.firstOrNull { it.type.isInstance(value) }
                ?.let { it as LiteralConverter<T> }
                ?.literal(value)
                ?: it.toString() // no literalizer for given value.
        } ?: "null"
    }

    /**
     * LiteralConverter.
     *
     * It is for basic Java/Kotlin classes.
     */
    private class LiteralConverter<T : Any>(
        val type: KClass<T>,
        private val toLiteral: (T) -> String,
    ) {
        fun literal(value: T): String = toLiteral(value)
    }

    // registered converters
    private val converters = mutableListOf<LiteralConverter<*>>()

    /**
     * Register a literal converter for a specific type.
     */
    private inline fun <reified T : Any> register(noinline block: (T) -> String) {
        converters.add(LiteralConverter(T::class, block))
    }


    /**
     * Join strings with separator.
     */
    private fun List<String>.joinToStringAutoWrap(separator: String, prefix: String, postfix: String): String {
        return if (sumOf { it.length } > 80) {
            joinToString(separator + "\n\t", prefix + "\n\t", "\n" + postfix)
        } else {
            joinToString(separator, prefix, postfix)
        }
    }

    private fun unescape(string: String) =
        string.replace("\\", "\\\\")
            .replace("\n", "\\n")
            .replace("\r", "\\r")
            .replace("\t", "\\t")


    init {
        // register built-in literal converters.
        // the order of those is important!
        register<Int> { "$it" }
        register<Long> { "${it}L" }
        register<Double> { "$it" }
        register<Float> { "${it}f" }
        register<Char> { "'${unescape(it.toString())}'" }
        register<String> { "\"${unescape(it)}\"" }
        register<Regex> { "/$it/" }
        register<Boolean> { "$it" }
        register<Throwable> { "${it::class.qualifiedName}(message=${literal(it.message)})" }
        register<Array<Any?>> { array ->
            array.map { literal(it) }
                .joinToStringAutoWrap(separator = ",", prefix = "[", postfix = "]")
        }
        register<ByteArray> { bytes ->
            bytes.joinToString(separator = " ", prefix = "[", postfix = "]") { byte ->
                String.format("0x%02X", byte)
            }
        }
        register<Collection<Any?>> { collection ->
            collection.map { literal(it) }
                .joinToStringAutoWrap(separator = ",", prefix = "${collection::class.simpleName}(", postfix = ")")
        }
        register<Map<Any?, Any?>> { map ->
            map.map { "${literal(it.key)}:${literal(it.value)}" }
                .joinToStringAutoWrap(separator = ",", prefix = "${map::class.simpleName}{", postfix = "}")
        }
        register<ClosedRange<Comparable<Any?>>> { "(${literal(it.start)}..${literal(it.endInclusive)})" }
        // time
        register<Temporal> { "${it.javaClass.simpleName}<$it>" }
        register<Date> { "Date<$it>" }
        register<Duration> { "Duration<${it.toString().replace(" ", "")}>" }
        register<java.time.Duration> { "Duration<${it.toString().substring(2).lowercase()}>" }
    }

}
