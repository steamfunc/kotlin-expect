package io.github.steamfunc.expect.extension

import io.github.steamfunc.expect.Expect

/**
 * Extension: Collection
 *
 * @author Yunsang Choi
 */
public fun <E : Any?, T : Iterable<E>> Expect<T>.contain(item: E?): Unit =
    satisfyThat("contain <${item.literal}>") {
        it.contains(item)
    }

public fun <E : Any?, T : Iterable<E>> Expect<T>.containAny(predicate: (E) -> Boolean): Unit =
    satisfyThat("contain any satisfying given predicate") {
        it.any(predicate)
    }

public fun <E : Any?, T : Iterable<E>> Expect<T>.containAll(predicate: (E) -> Boolean): Unit =
    satisfyThat("contain all satisfying  given predicate") {
        it.all(predicate)
    }

public fun <E : Any?, T : Iterable<E>> Expect<T>.containNone(predicate: (E) -> Boolean): Unit =
    satisfyThat("contain none satisfying given predicate") {
        it.none(predicate)
    }


public fun <E : Any?, T : Collection<E>> Expect<T>.containAll(vararg items: E): Unit =
    satisfyThat("contain all of <${items.literal}>") {
        it.containsAll(items.toList())
    }

public fun <T : Collection<*>> Expect<T>.haveSizeOf(size: Int): Unit =
    satisfyThat("have size of <${size.literal}>") {
        it.size.asTestProp("size") == size
    }

public fun <T : Collection<*>> Expect<T>.beEmpty(): Unit =
    satisfyThat("be empty") {
        it.isEmpty()
    }

public fun <E : Any?, T : List<E>> Expect<T>.containAllInSameOrder(vararg items: E): Unit =
    satisfyThat("contain all <${items.literal}> in same order ") {
        if (!it.containsAll(items.toList())) false
        else {
            val indice = items.map { i -> it.indexOf(i) }
            indice.sorted() == indice
        }
    }

public fun <E : Comparable<E>, T : List<E>> Expect<T>.beSorted(): Unit =
    satisfyThat("be sorted") {
        it.sorted() == it
    }

public fun <E : Comparable<E>, T : List<E>> Expect<T>.beReverseSorted(): Unit =
    satisfyThat("be sorted in reverse order") {
        it.sorted().reversed() == it
    }


public fun <E : Any?, T : List<E>> Expect<T>.beSortedWith(comparator: Comparator<E>): Unit =
    satisfyThat("be sorted according to the given comparator") {
        it.sortedWith(comparator) == it
    }

public fun <E : Any?, T : List<E>> Expect<T>.beReverseSortedWith(comparator: Comparator<E>): Unit =
    satisfyThat("be sorted in reverse order according to the given comparator") {
        it.sortedWith(comparator).reversed() == it
    }

public fun <K : Any?, V : Any, T : Map<K, V>> Expect<T>.containKey(key: K?): Unit =
    satisfyThat("contain key <${key.literal}>") {
        it.containsKey(key)
    }

public fun <K : Any?, V : Any, T : Map<K, V>> Expect<T>.containKeys(vararg keys: K): Unit =
    satisfyThat("contain keys <${keys.literal}>") {
        keys.all { key -> it.containsKey(key) }
    }

public fun <K : Any?, V : Any, T : Map<K, V>> Expect<T>.containValue(value: V?): Unit =
    satisfyThat("contain value <${value.literal}>") {
        it.containsValue(value)
    }

public fun <K : Any?, V : Any, T : Map<K, V>> Expect<T>.containValues(vararg values: V?): Unit =
    satisfyThat("contain values <${values.literal}>") {
        values.all { value -> it.containsValue(value) }
    }


public fun <K : Any?, V : Any, T : Map<K, V>> Expect<T>.containEntry(key: K?, value: V?): Unit =
    satisfyThat("contain entry <${key.literal}:${value.literal}>") {
        it.containsKey(key) && it[key] == value
    }

public fun <K : Any?, V : Any, T : Map<K, V>> Expect<T>.containEntries(vararg pairs: Pair<K?, V?>): Unit =
    satisfyThat("contain entries <${pairs.literal}>") {
        pairs.all { pair -> it.containsKey(pair.first) && it[pair.first] == pair.second }
    }

public fun <K : Any?, V : Any, T : Map<K, V>> Expect<T>.haveEntries(size: Int): Unit =
    satisfyThat("have size of <${size.literal}>") {
        it.size.asTestProp("size") == size
    }

