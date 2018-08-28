// !LANGUAGE: +AllowContractsForCustomFunctions +UseCallsInPlaceEffect
// !DIAGNOSTICS: -INVISIBLE_REFERENCE -INVISIBLE_MEMBER

/*
 KOTLIN DIAGNOSTICS NOT LINKED SPEC TEST (POSITIVE)

 SECTION: Contracts
 CATEGORY: analysis, controlflow, initialization
 NUMBER: 5
 DESCRIPTION: Smart initialization with correspond contract function with default value before lambda.
 ISSUES: KT-26444
 */

// FILE: contracts.kt

package contracts

import kotlin.internal.contracts.*

fun case_1(x: Double = 1.0, block: () -> Unit): Double {
    contract { callsInPlace(block, InvocationKind.EXACTLY_ONCE) }
    return x
}

// FILE: usages.kt

import contracts.*

fun case_1() {
    val value_1: Int
    contracts.case_1 { value_1 = 10 }
    value_1.inc()
}
