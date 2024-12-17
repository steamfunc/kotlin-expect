package io.github.steamfunc.extension

import io.github.steamfunc.Expect
import java.util.*

/**
 * Extension: ByteArray.
 *
 * @author Yunsang Choi
 */


public fun Expect<ByteArray>.contain(item: Byte): Unit =
    satisfyThat("contain <${item.literal}>") {
        it.contains(item)
    }

public fun Expect<ByteArray>.be(other: ByteArray): Unit =
    satisfyThat("be <${other.literal}>") {
        it.contentEquals(other)
    }

// alias for `be`
public fun Expect<ByteArray>.beSameContentOf(other: ByteArray): Unit = this.be(other)

public fun Expect<ByteArray>.haveSizeOf(size: Int): Unit =
    satisfyThat("have size of <${size.literal}>") {
        it.size == size
    }

public fun Expect<ByteArray>.beEmpty(): Unit =
    satisfyThat("be empty") {
        it.isEmpty()
    }

public fun Expect<ByteArray>.beEncodedBase64(base64: String): Unit =
    satisfyThat("be as base64") {
        Base64.getEncoder().encodeToString(it) == base64
    }

public fun Expect<ByteArray>.beAsHex(hexBin: String): Unit =
    satisfyThat("be as HEX '${hexBin.literal}'") {
        val hex = it.joinToString("") { String.format("%02X", it) }
        hex.equals(hexBin, ignoreCase = true)
    }


