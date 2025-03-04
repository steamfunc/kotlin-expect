package io.github.steamfunc.expect

import io.github.steamfunc.expect.extension.be
import io.github.steamfunc.expect.extension.startWith
import org.junit.jupiter.api.Test
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.time.Instant
import java.time.LocalDateTime
import java.util.*

class LiteralsTest {
    private val log: Logger = LoggerFactory.getLogger(this.javaClass)

    @Test
    fun `test for string`() {
        Literals.literal("hello")
            .should.be("\"hello\"")

        Literals.literal("hello\tworld")
            .should.be("\"hello\\tworld\"")
    }

    @Test
    fun `test for char`() {
        Literals.literal('c')
            .should.be("'c'")
    }

    @Test
    fun `test for list`() {
        Literals.literal(arrayListOf(1, 2, 3))
            .should.be("ArrayList(1,2,3)")
        Literals.literal(LinkedList<Int>(listOf(1, 2, 3)))
            .should.be("LinkedList(1,2,3)")
    }

    @Test
    fun `test for numeric`() {
        Literals.literal(1).should.be("1")
        Literals.literal(2L).should.be("2L")
        Literals.literal(3.1).should.be("3.1")
        Literals.literal(4.12f).should.be("4.12f")
    }

    @Test
    fun `test for array`() {
        Literals.literal(arrayOf(1L, 2L, 3L))
            .should.be("[1L,2L,3L]")
    }

    @Test
    fun `test for regex`() {
        Literals.literal(Regex("^hello.*!$"))
            .should.be("/^hello.*!$/")
    }

    @Test
    fun `test for map`() {
        Literals.literal(linkedMapOf("KOREA" to "SEOUL", "SPAIN" to "MADRID"))
            .should.be("LinkedHashMap{\"KOREA\":\"SEOUL\",\"SPAIN\":\"MADRID\"}")
    }

    @Test
    fun `test for throwable`() {
        val aException = NoSuchElementException("it's just test")

        Literals.literal(aException)
            .should.be("java.util.NoSuchElementException(message=\"it's just test\")")
    }

    @Test
    fun `test for times`() {
        Literals.literal(Instant.now()).should.startWith("Instant")
        Literals.literal(Date()).should.startWith("Date")
        Literals.literal(LocalDateTime.now()).should.startWith("LocalDateTime")
    }

    @Test
    fun `test for StringRepresentable`() {
        class MyClass(val value: String) : StringRepresentable {
            override fun toLiteral(): String {
                return "MyClass<$value>"
            }
        }

        Literals.literal(MyClass("hello"))
            .should.be("MyClass<hello>")

    }
}
