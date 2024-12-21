# kotlin-expect

[![CI Build](https://github.com/steamfunc/kotlin-expect/actions/workflows/ci-build.yml/badge.svg)](https://github.com/steamfunc/kotlin-expect/actions/workflows/ci-build.yml)  
[![codecov](https://codecov.io/github/steamfunc/kotlin-expect/branch/develop/graph/badge.svg?token=C20OB3IIBC)](https://codecov.io/github/steamfunc/kotlin-expect)

Translate: [한국어](README.ko.md)

`kotlin-expect` is an assertion DSL designed for Kotlin testing.  
Inspired by [RSpec Expectations], it offers a readable and easy-to-write assertion DSL, while also simplifying the
process of defining custom assertion DSLs, similar to [Hamcrest]'s Custom Matchers.

## Brief History

This project began as a fork of [`net.oddpoet:kotlin-expect`], developed by the same author (Yunsang Choi). The original
library is no longer maintained, and all versions beyond `1.4.0` are managed in this repository. The following changes
were made after the fork:

- The package name was changed from `net.oddpoet` to `io.github.steamfunc`.
- The minimum Kotlin version was updated to `1.9`.
- The minimum Java version was updated to `11`.
- The documentation tool was switched to [Dokka].
- The repository migration was necessary due to changes in Maven Central Repository's deployment policies, requiring a
  reconfiguration.

## Requirements

- Kotlin: Version 1.9 or higher
- Java: Version 11 or higher

## Setup

Add the following dependency to your Gradle configuration:

```kotlin
dependencies {
    testCompile("io.github.steamfunc:kotlin-expect:<VERSION>")
}
```

## Basic Usage

### `expect(<subj>).to`

Similar to [RSpec Expectations], you can write assertions for a *subject* using the `expect(subject).to` syntax:

```kotlin
val aList = listOf(1, 2, 3)
expect(aList).to.haveSizeOf(3)
expect(aList).to.contain(2)
expect(aList).to.not.contain(5)
expect(aList).to.beSorted()

expect("hello").to.startWith("h")
expect("hello").to.not.endWith("x", ignoreCase = true)
expect("hello").to.not.beNull()

expect(123).to.beIn(100..200)
expect(123).to.beGreaterThan(100)

expect(1 == 2).to.beFalse()
```

The assertion methods (e.g., `beGreaterThan`) following `to` vary depending on the type of the *subject*. For
user-defined objects, you can define [custom assertion methods](#custom-assertions).

### `<subj>.should`

As with [RSpec Expectations], you can write assertions in a more concise manner using `<subject>.should.<assertion>`:

```kotlin
val aList = listOf(1, 2, 3)
aList.should.haveSizeOf(3)
aList.should.contain(2)
aList.should.not.contain(5)
aList.should.beSorted()

"hello".should.startWith("h")
"hello".should.not.endWith("x", ignoreCase = true)
"hello".should.not.beNull()

123.should.beIn(100..200)
123.should.beGreaterThan(100)

(1 == 2).should.beFalse()
```

Both `expect(<subject>).to` and `<subject>.should` provide the same functionality, so you can choose whichever suits
your preferences.

### `expect(<subj>) { ... }`

Write multiple assertions for a *subject* using `expect(<subj>) { ... }`. The *subject* must not be null; otherwise, an
`AssertionError` will be thrown.

```kotlin
expect(aList) {
    it.should.haveSizeOf(3)
    it.should.not.contain(5)
    it.should.containAny { it < 10 }
}
```

### `expect { ... }.throws`

You can assert exceptions using the `expect { ... }.throws` syntax:

```kotlin
expect {
    throw IOException()
}.throws() // An exception must occur; otherwise, an AssertionError will be thrown.

expect {
    throw IOException()
}.throws<Exception>() // Same as `.throws()`

expect {
    throw NoSuchFileException("file.txt")
}.throws<IOException> {
    it.message.should.be("file.txt") // Assert properties of the exception
    it.should.causedBy<NoSuchFileException>() // Exception must be of type `NoSuchFileException`
}
```

### `expect { ... }.elapsedWithin()`

You can assert the execution time of a block using the `expect { ... }.elapsedWithin()` syntax:

```kotlin
expect {
    Thread.sleep(100)
}.elapsedWithin(50.milliseconds, 150.milliseconds) // Must complete within 50ms to 150ms; otherwise, AssertionError.
```

## Custom Assertions

`kotlin-expect` provides a wide range of built-in assertions for Java/Kotlin types. It also offers extensibility for
defining your own assertions easily.

You can define custom assertions for your classes using the `satisfyThat` function, which performs an assertion on the
*subject* of an `Expect` instance and returns a `Boolean`. You can also provide a custom message for the assertion log.

Here’s an example:

```kotlin
// For your classes
abstract class Person(
    val name: String,
    val birthdate: LocalDate
)

class Employee(
    name: String, birthdate: LocalDate,
    val empNo: String?,
    val dept: String?
) : Person(name, birthdate)
```

```kotlin
// Custom assertions
fun <T : Person> Expect<T>.beUnderage() =
    satisfyThat("be underage") {
        it.birthdate.plusYears(19) > LocalDate.now()
    }

fun Expect<Employee>.beValid() =
    satisfyThat("be valid") {
        it.empNo != null && it.dept != null
    }

fun Expect<Employee>.beAssignedTo(dept: String) =
    satisfyThat("be assigned to $dept") {
        it.dept == dept
    }
```

```kotlin
// Usage of custom assertions
val emp = Employee(
    "yunsang.choi",
    LocalDate.of(1976, 4, 2),
    "X00000",
    "DevTeam"
)
expect(emp) {
    it.should.beValid()
    it.should.not.beUnderage()
    it.should.not.beAssignedTo("DesignTeam")
}
```

[RSpec Expectations]: https://github.com/rspec/rspec-expectations

[`net.oddpoet:kotlin-expect`]: https://github.com/oddpoet/kotlin-expect

[Dokka]: https://github.com/Kotlin/dokka

[Hamcrest]: https://hamcrest.org/JavaHamcrest/
