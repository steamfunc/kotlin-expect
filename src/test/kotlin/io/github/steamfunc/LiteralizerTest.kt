package io.github.steamfunc

import io.github.steamfunc.extension.be
import io.github.steamfunc.extension.startWith
import org.junit.jupiter.api.Test
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.time.Instant
import java.time.LocalDateTime
import java.util.*

class LiteralizerTest {
    private val log: Logger = LoggerFactory.getLogger(this.javaClass)

    @Test
    fun `test for string`() {
        LiteralConverter.literal("hello")
            .should.be("\"hello\"")

        LiteralConverter.literal("hello\tworld")
            .should.be("\"hello\\tworld\"")
    }

    @Test
    fun `test for char`() {
        LiteralConverter.literal('c')
            .should.be("'c'")
    }

    @Test
    fun `test for list`() {
        LiteralConverter.literal(arrayListOf(1, 2, 3))
            .should.be("ArrayList(1,2,3)")
        LiteralConverter.literal(LinkedList<Int>(listOf(1, 2, 3)))
            .should.be("LinkedList(1,2,3)")
    }

    @Test
    fun `test for numeric`() {
        LiteralConverter.literal(1).should.be("1")
        LiteralConverter.literal(2L).should.be("2L")
        LiteralConverter.literal(3.1).should.be("3.1")
        LiteralConverter.literal(4.12f).should.be("4.12f")
    }

    @Test
    fun `test for array`() {
        LiteralConverter.literal(arrayOf(1L, 2L, 3L))
            .should.be("[1L,2L,3L]")
    }

    @Test
    fun `test for regex`() {
        LiteralConverter.literal(Regex("^hello.*!$"))
            .should.be("/^hello.*!$/")
    }

    @Test
    fun `test for map`() {
        LiteralConverter.literal(linkedMapOf("KOREA" to "SEOUL", "SPAIN" to "MADRID"))
            .should.be("LinkedHashMap{\"KOREA\":\"SEOUL\",\"SPAIN\":\"MADRID\"}")
    }

    @Test
    fun `test for throwable`() {
        val aException = NoSuchElementException("it's just test")

        LiteralConverter.literal(aException)
            .should.be("java.util.NoSuchElementException(message=\"it's just test\")")
    }

    @Test
    fun `test for times`() {
        LiteralConverter.literal(Instant.now()).should.startWith("Instant")
        LiteralConverter.literal(Date()).should.startWith("Date")
        LiteralConverter.literal(LocalDateTime.now()).should.startWith("LocalDateTime")
    }

    @Test
    fun `test for StringRepresentable`() {
        class MyClass(val value: String) : StringRepresentable {
            override fun toLiteral(): String {
                return "MyClass<$value>"
            }
        }

        LiteralConverter.literal(MyClass("hello"))
            .should.be("MyClass<hello>")

    }
}
