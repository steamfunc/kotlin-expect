package io.github.steamfunc

/**
 * StringRepresentable.
 *
 * This interface is used to convert an object to a string literal
 * when it is used in expect message.
 *
 * To define how an object is represented in an expect message, implement this interface.
 *
 * @author Yunsang Choi
 */
public interface StringRepresentable {
    public fun toLiteral(): String
}