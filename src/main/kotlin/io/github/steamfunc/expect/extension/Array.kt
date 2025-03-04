package io.github.steamfunc.expect.extension

import io.github.steamfunc.expect.Expect

/**
 * Extension: Array
 *
 * @author Yunsang Choi
 */

public fun <E : Any?> Expect<Array<E>>.contain(item: E?): Unit =
    satisfyThat("contain <${item.literal}>") {
        it.contains(item)
    }

public fun <E : Any?> Expect<Array<E>>.haveSizeOf(size: Int): Unit =
    satisfyThat("have size of <${size.literal}>") {
        it.size == size
    }

public fun <E : Any?> Expect<Array<E>>.containAllInSameOrder(vararg items: E): Unit =
    satisfyThat("contain all <${items.literal}> in same order ") {
        if (!it.toList().containsAll(items.toList())) false
        else {
            val indice = items.map { i -> it.indexOf(i) }
            indice.sorted() == indice
        }
    }

public fun <E : Any?> Expect<Array<E>>.beEmpty(): Unit =
    satisfyThat("be empty") {
        it.isEmpty()
    }

public fun <E : Any?> Expect<Array<E>>.containAll(vararg items: E): Unit =
    satisfyThat("contain all of <${items.literal}>") {
        it.toList().containsAll(items.toList())
    }


public fun <E : Any?> Expect<Array<E>>.containAny(predicate: (E) -> Boolean): Unit =
    satisfyThat("contain any satisfying given predicate") {
        it.any(predicate)
    }

public fun <E : Any?> Expect<Array<E>>.containAll(predicate: (E) -> Boolean): Unit =
    satisfyThat("contain all satisfying  given predicate") {
        it.all(predicate)
    }

public fun <E : Any?> Expect<Array<E>>.containNone(predicate: (E) -> Boolean): Unit =
    satisfyThat("contain none satisfying given predicate") {
        it.none(predicate)
    }

public fun <E : Comparable<E>> Expect<Array<E>>.beSorted(): Unit =
    satisfyThat("be sorted") {
        it.sorted() == it.toList()
    }

public fun <E : Comparable<E>> Expect<Array<E>>.beReverseSorted(): Unit =
    satisfyThat("be sorted in reverse order") {
        it.sorted().reversed() == it.toList()
    }


public fun <E : Any?> Expect<Array<E>>.beSortedWith(comparator: Comparator<E>): Unit =
    satisfyThat("be sorted according to the given comparator") {
        it.sortedWith(comparator) == it.toList()
    }

public fun <E : Any?> Expect<Array<E>>.beReverseSortedWith(comparator: Comparator<E>): Unit =
    satisfyThat("be sorted in reverse order according to the given comparator") {
        it.sortedWith(comparator).reversed() == it.toList()
    }