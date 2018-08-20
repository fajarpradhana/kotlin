// !LANGUAGE: +ReleaseCoroutines
// !API_VERSION: 1.3
// IGNORE_BACKEND: JVM_IR
// IGNORE_BACKEND: JS_IR
// TODO: muted automatically, investigate should it be ran for JS or not
// IGNORE_BACKEND: JS, NATIVE

// WITH_REFLECT

import kotlin.test.assertTrue
import kotlin.test.assertFalse
import kotlin.reflect.full.isSuspend
import kotlin.reflect.KCallable

inline fun inline() {}
class External { external fun external() }
operator fun Unit.invoke() {}
infix fun Unit.infix(unit: Unit) {}
class Suspend { suspend fun suspend() {} }

val externalGetter = Unit
    external get

inline var inlineProperty: Unit
    get() = Unit
    set(value) {}

fun box(): String {
    assertTrue(::inline.isInline)
    assertFalse(::inline.isExternal)
    assertFalse(::inline.isOperator)
    assertFalse(::inline.isInfix)
    assertFalse(::inline.isSuspend)
    assertFalse((::inline as KCallable<*>).isSuspend)

    assertFalse(External::external.isInline)
    assertTrue(External::external.isExternal)
    assertFalse(External::external.isOperator)
    assertFalse(External::external.isInfix)
    assertFalse(External::external.isSuspend)
    assertFalse((External::external as KCallable<*>).isSuspend)

    assertFalse(Unit::invoke.isInline)
    assertFalse(Unit::invoke.isExternal)
    assertTrue(Unit::invoke.isOperator)
    assertFalse(Unit::invoke.isInfix)
    assertFalse(Unit::invoke.isSuspend)
    assertFalse((Unit::invoke as KCallable<*>).isSuspend)

    assertFalse(Unit::infix.isInline)
    assertFalse(Unit::infix.isExternal)
    assertFalse(Unit::infix.isOperator)
    assertTrue(Unit::infix.isInfix)
    assertFalse(Unit::infix.isSuspend)
    assertFalse((Unit::infix as KCallable<*>).isSuspend)

    assertFalse(Suspend::suspend.isInline)
    assertFalse(Suspend::suspend.isExternal)
    assertFalse(Suspend::suspend.isOperator)
    assertFalse(Suspend::suspend.isInfix)
    assertTrue(Suspend::suspend.isSuspend)
    assertTrue((Suspend::suspend as KCallable<*>).isSuspend)

    assertTrue(::externalGetter.getter.isExternal)
    assertFalse(::externalGetter.getter.isInline)
    assertFalse(::externalGetter.getter.isSuspend)
    assertFalse(::externalGetter.isSuspend)

    assertFalse(::inlineProperty.getter.isExternal)
    assertTrue(::inlineProperty.getter.isInline)
    assertTrue(::inlineProperty.setter.isInline)
    assertFalse(::inlineProperty.getter.isSuspend)
    assertFalse(::inlineProperty.setter.isSuspend)
    assertFalse(::inlineProperty.isSuspend)

    return "OK"
}
