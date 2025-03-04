package io.github.steamfunc.expect.extension

import io.github.steamfunc.expect.Expect
import java.io.File

/**
 * Extension: File
 * @author Yunsang Choi
 */

public fun Expect<File>.beDirectory(): Unit =
    satisfyThat("be directory") {
        it.isDirectory
    }

public fun Expect<File>.beFile(): Unit =
    satisfyThat("be file") {
        it.isFile
    }

public fun Expect<File>.exist(): Unit =
    satisfyThat("exist") {
        it.exists()
    }

public fun Expect<File>.beAbsolute(): Unit =
    satisfyThat("be absolute") {
        it.isAbsolute
    }

public fun Expect<File>.beHidden(): Unit =
    satisfyThat("be hidden") {
        it.isHidden
    }

public fun Expect<File>.beExecutable(): Unit =
    satisfyThat("be executable") {
        it.canExecute()
    }


public fun Expect<File>.beReadable(): Unit =
    satisfyThat("be readable") {
        it.canRead()
    }

public fun Expect<File>.beWritable(): Unit =
    satisfyThat("be writable") {
        it.canWrite()
    }

public fun Expect<File>.beRooted(): Unit =
    satisfyThat("be rooted") {
        it.isRooted
    }

public fun Expect<File>.haveNameOf(name: String): Unit =
    satisfyThat("have name of <${name.literal}>") {
        it.name == name
    }

public fun Expect<File>.haveExtensionOf(extension: String): Unit =
    satisfyThat("have extension of <${extension.literal}>") {
        it.extension.asTestProp("extension") == extension
    }

public fun Expect<File>.bePathOf(path: String): Unit =
    satisfyThat("be path of <${path.literal}>") {
        it.path.asTestProp("path") == path
    }

public fun Expect<File>.haveLengthOf(length: Long): Unit =
    satisfyThat("have length of <${length.literal}>") {
        it.length().asTestProp("length") == length
    }

public fun Expect<File>.startWith(other: File): Unit =
    satisfyThat("start with <${other.literal}>") {
        it.startsWith(other)
    }

public fun Expect<File>.startWith(other: String): Unit =
    satisfyThat("start with <${other.literal}>") {
        it.startsWith(other)
    }

public fun Expect<File>.endWith(other: File): Unit =
    satisfyThat("end with <${other.literal}>") {
        it.endsWith(other)
    }

public fun Expect<File>.endWith(other: String): Unit =
    satisfyThat("end with <${other.literal}>") {
        it.endsWith(other)
    }






