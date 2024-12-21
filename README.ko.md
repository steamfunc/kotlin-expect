# kotlin-expect

[![CI Build](https://github.com/steamfunc/kotlin-expect/actions/workflows/ci-build.yml/badge.svg)](https://github.com/steamfunc/kotlin-expect/actions/workflows/ci-build.yml)
[![codecov](https://codecov.io/github/steamfunc/kotlin-expect/branch/develop/graph/badge.svg?token=C20OB3IIBC)](https://codecov.io/github/steamfunc/kotlin-expect)

`kotlin-expect`는 kotlin test를 위한 assertion DSL 입니다.
[Rspec Expectation]의 영향을 받아 가독성 있고 작성도 편한 assertion DSL을 제공하며,
[Hamcrest]의 Custom Matcher와 같은 custom assertion DSL을 보다 쉽게 정의할 수 있습니다.

## 짧은 역사

이 프로젝트는 동일한 개발자(Yunsang Choi)가 개발한 [`net.oddpoet:kotlin-expect`]를 fork하여 시작되었습니다.
더이상 [`net.oddpoet:kotlin-expect`]는 유지보수하지 않으며, `1.4.0` 이후 버전은 이 repository에서 관리됩니다.
fork 이후 다음과 같은 변경사항이 있습니다.

- 패키지가 `net.oddpoet`에서 `io.github.steamfunc`로 변경되었습니다.
- kotlin 최소버전이 `1.9`로 변경되었습니다.
- java 최소버전이 `11`로 변경되었습니다.
- javadoc 생성툴을 [Dokka]로 변경하였습니다.
- repository를 변경한 이유는 Maven Central Repository의 배포정책 변경으로 인해 설정을 새로이 구성해야했기 때문입니다.

## Requirements

- kotlin: 1.9 이상
- java: 11 이상

## Setup

gradle dependency 설정은 다음과 같습니다.

```gradle
dependencies {
    testCompile("io.github.steamfunc:kotlin-expect:<VERSION>")
}
```

## 기본 사용법

### `expect(<subj>).to`

[Rspec Expectation]과 유사하게 `expect(subject).to` 형태로 *subject*에 대한 assertion을 작성할 수 있습니다.

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

`to` 뒤의 assertion method(e.g. `beGreaterThan`)들은 *subject*의 타입에 따라 다양하게 제공되며,
사용자 정의 클래스의 객체의 경우, 그 클래스를 위한 [custom assertion method](#사용자-정의-Assertion)를 정의할 수 있습니다.

### `<subj>.should`

[Rspec Expectation]과 마찬가지로 `<subject>.should.<assertion>` 형태로 보다 간단하게 assertion을 작성할 수 있습니다.

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

`expect(<subject>).to`와 `<subject>.should`는 동일한 기능을 제공하며, 사용자는 더 편한 방법을 선택하여 사용할 수 있습니다.

### `expect(<subj>) {...}`

`expect(<subj>) {...}` 형태로 *subject*에 대한 여러 assertion을 작성할 수 있습니다.
이 때 *subject*는 not-null이어야 합니다. *subject*가 null일 경우 이 block은 `AssertionError`를 발생시킵니다.

```kotlin
expect(aList) {
    it.should.haveSizeOf(3)
    it.should.not.contain(5)
    it.should.containAny { it < 10 }
}
```

### `expect { ... }.throws`

`expect { ... }.throws` 형태로 예외에 대한 assertion을 작성할 수 있습니다.

```kotlin
expect {
    throw IOExpection()
}.throws() // 예외가 발생해야 함. 그렇지 않으면 AssertionError 발생.

expect {
    throw IOExpection()
}.throws<Exception>() // 위의 .throws()와 동일

expect {
    throw NoSuchFileException("file.txt")
}.throws<IOExcpetion> { // 예외발생시 예외 객체가 주어짐(`it`)
    it.message.should.be("file.txt") // 발생한 예외에 대한 assert
    it.should.causedBy<NoSuchFileException>() // 발생한 예외가 `NoSuchFileException`이어야 함
}

```

### `expect { ... }.elapsedWithin/elapseAtMost`

`expect { ... }.elapsedWithin` 혹은 `expect { ... }.elapsedAtMost` 형태로 코드 실행시간에 대한 assertion을 작성할 수 있습니다.

```kotlin
expect {
    Thread.sleep(100)
}.elapsedWithin(50.milliseconds, 150.milliseconds) // 50ms ~ 150ms 사이에 실행되어야 함. 그렇지 않으면 AssertionError 발생.

expect {
    Thread.sleep(100)
}.elapsedAtMost(150.milliseconds) // 150ms 이내에 실행되어야 함, 아니면 AssertionError.
```

## 사용자 정의 Assertion

`kotlin-expect`는 java/kotlin의 기본 타입들에 대한 다양한 built-in assertion을 제공합니다.
그에 더해, `kotlin-expect`는 간단하게 사용자가 자신만의 assertion을 정의할 수 있도록 확장성을 제공합니다.

사용자 정의 assertion은 `Expect` 클래스에 대한 extension을 `satisfyThat` 함수를 이용하여 정의할 수 있습니다.
`satisfyThat` 함수는 `Expect`의 *subject*에 대한 assertion을 수행하고, 그 결과를 `Boolean`으로 반환합니다.
또한 `satisfyThat` 함수에 assertion시 로그에 사용될 메시지를 제공할 수 있습니다.

아래의 예를 참고하세요.

```kotlin
// for your classes
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
// you can write your own assertion
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
// then you can use your assertion.
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

[Rspec Expectation]:https://github.com/rspec/rspec-expectations

[`net.oddpoet:kotlin-expect`]:https://github.com/oddpoet/kotlin-expect

[Dokka]:https://github.com/Kotlin/dokka

[Hamcrest]:https://hamcrest.org/JavaHamcrest/
