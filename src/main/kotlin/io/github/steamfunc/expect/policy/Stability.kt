package io.github.steamfunc.expect.policy

/**
 * Stability annotation.
 *
 * It is used to indicate the maturity of a class, interface or method.
 *
 * @author Yunsang Choi
 */
internal interface Stability {
    /**
     * Stable.
     *
     * This annotation ensures compatibility at the major version level.
     */
    @MustBeDocumented
    @Retention(AnnotationRetention.RUNTIME)
    annotation class Stable

    /**
     * Experimental.
     *
     * Indicates that a feature under development.
     *
     * This annotation ensures compatibility at the major version level.
     */
    @MustBeDocumented
    @Retention(AnnotationRetention.RUNTIME)
    annotation class Experimental

    /**
     * DoNotTrust.
     *
     * Not recommend to use.
     */
    @MustBeDocumented
    @Retention(AnnotationRetention.RUNTIME)
    annotation class DoNotTrust

}