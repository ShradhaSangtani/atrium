package ch.tutteli.atrium.specs.integration

//import ch.tutteli.atrium.api.cc.en_GB.toBe
//import ch.tutteli.atrium.assertions.DescriptiveAssertion
//import ch.tutteli.atrium.reporting.RawString
//import ch.tutteli.atrium.spec.prefixedDescribe
//import ch.tutteli.atrium.spec.setUp
import ch.tutteli.atrium.api.cc.en_GB.messageContains
import ch.tutteli.atrium.api.cc.en_GB.toBe
import ch.tutteli.atrium.api.cc.en_GB.toThrow
import ch.tutteli.atrium.core.coreFactory
import ch.tutteli.atrium.creating.Expect
import ch.tutteli.atrium.domain.builders.AssertImpl
import ch.tutteli.atrium.domain.creating.throwable.thrown.ThrowableThrown
import ch.tutteli.atrium.reporting.reporter
import ch.tutteli.atrium.reporting.translating.Untranslatable
import ch.tutteli.atrium.specs.*
import ch.tutteli.atrium.specs.verbs.AssertionVerbFactory
import ch.tutteli.atrium.translations.DescriptionAnyAssertion.TO_BE
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.Suite
//import ch.tutteli.atrium.translations.DescriptionComparableAssertion

abstract class AnyAssertionsSpec(
    verbs: AssertionVerbFactory,
    funInt: AnyAssertionsSpecFunFactory<Int>,
//    funNullableInt: AnyAssertionsSpecFunFactory<Int?>,
    funDataClass: AnyAssertionsSpecFunFactory<DataClass>,
    toBe: String,
//    notToBe: String,
//    isSame: String,
//    isNotSame: String,
//    toBeNullPair: Pair<String, Expect<Int?>.() -> Unit>,
//    toBeNullablePair: Pair<String, Expect<Int?>.(Int?) -> Unit>,
//    toBeNullIfNullElsePair: Pair<String, Expect<Int?>.((Expect<Int>.() -> Unit)?) -> Unit>,
//    andPair: Pair<String, Expect<Int>.() -> Expect<Int>>,
//    andLazyPair: Pair<String, Expect<Int>.(Expect<Int>.() -> Unit) -> Expect<Int>>,
    describePrefix: String = "[Atrium] "
) : Spek({

    include(object : SubjectLessAssertionSpec<Int>(describePrefix,
        toBe to expectLambda { funInt.toBeFun(this, 1) }
    ) {})

    //TODO add nullable case
//    include(object : SubjectLessAssertionSpec<Int?>(describePrefix,
//        toBe to expectLambda { funNullableInt.toBeFun(this, 1) }
//    ) {})
//
//    include(object : SubjectLessAssertionSpec<Int>(describePrefix,
//
//        notToBe to mapToCreateAssertion { funInt.notToBeFun(this, 1) },
//        isSame to mapToCreateAssertion { funInt.isSameFun(this, 1) },
//        isNotSame to mapToCreateAssertion { funInt.isNotSameFun(this, 1) },
//        andPair.first to mapToCreateAssertion { andPair.second },
//        andLazyPair.first to mapToCreateAssertion { andLazyPair.second }
//    ) {})
//
    include(object : CheckingAssertionSpec<Int>(verbs, describePrefix,
        checkingTriple(toBe, { funInt.toBeFun(this, 1) }, 1, 0)
//        checkingTriple(notToBe, { funInt.notToBeFun(this, 1) }, 0, 1),
//        checkingTriple(isSame, { funInt.isSameFun(this, 1) }, 1, 0),
//        checkingTriple(isNotSame, { funInt.isNotSameFun(this, 1) }, 0, 1)
    ) {})

//    fun prefixedDescribe(description: String, body: Suite.() -> Unit)
//        = prefixedDescribe(describePrefix, description, body)

    fun describeFun(vararg funName: String, body: Suite.() -> Unit) =
        describeFunTemplate(describePrefix, funName, body = body)

    val expect = verbs::checkException
    val assert: (Int) -> Expect<Int> = verbs::checkImmediately
//    val assertNullable: (Int?) -> Expect<Int?> = verbs::checkImmediately
//    val (toBeNull, toBeNullFun) = toBeNullPair
//    val (toBeNullable, toBeNullableFun) = toBeNullablePair
//    val (toBeNullIfNullElse, toBeNullIfNullElseFun) = toBeNullIfNullElsePair
//    val (and, andProperty) = andPair
//    val (andLazy, andLazyGroup) = andLazyPair

    describeFun(toBe) {
        //, notToBe, isSame, isNotSame) {

        context("primitive") {
            val toBeFun: Expect<Int>.(Int) -> Expect<Int> = funInt.toBeFun
//            val notToBeFun: Expect<Int>.(Int) -> Expect<Int> = funInt.notToBeFun
//            val isSameFun: Expect<Int>.(Int) -> Expect<Int> = funInt.isSameFun
//            val isNotSameFun: Expect<Int>.(Int) -> Expect<Int> = funInt.isNotSameFun

            context("one equals the other") {
                it("$toBe does not throw") {
                    assert(1).toBeFun(1)
                }
//                test("$isSame does not throw") {
//                    assert(1).isSameFun(1)
//                }
//                test("$notToBe throws AssertionError") {
//                    expect {
//                        assert(1).notToBeFun(1)
//                    }.toThrow<AssertionError> { messageContains(NOT_TO_BE.getDefault()) }
//                }
//                test("$isNotSame throws AssertionError") {
//                    expect {
//                        assert(1).isNotSameFun(1)
//                    }.toThrow<AssertionError> { messageContains(IS_NOT_SAME.getDefault()) }
//                }
            }
            context("one does not equal the other") {
                it("$toBe throws AssertionError") {
                    expect {
                        assert(1).toBeFun(2)
                    }.toThrow<AssertionError> { messageContains(TO_BE.getDefault()) }
                }
//                test("$notToBe does not throw") {
//                    assert(1).notToBeFun(2)
//                }
//                test("$isSame throws AssertionError") {
//                    expect {
//                        assert(1).isSameFun(2)
//                    }.toThrow<AssertionError> { messageContains(IS_SAME.getDefault()) }
//                }
//                test("$isNotSame does not throw") {
//                    assert(1).isNotSameFun(2)
//                }
            }
        }
        context("class") {
            val test = DataClass(true)
            val fluent = verbs.checkImmediately(test)
            val toBeFun: Expect<DataClass>.(DataClass) -> Expect<DataClass> = funDataClass.toBeFun
//            val notToBeFun: Expect<DataClass>.(DataClass) -> Expect<DataClass> = funDataClass.notToBeFun
//            val isSameFun: Expect<DataClass>.(DataClass) -> Expect<DataClass> = funDataClass.isSameFun
//            val isNotSameFun: Expect<DataClass>.(DataClass) -> Expect<DataClass> = funDataClass.isNotSameFun
            context("same") {
                it("$toBe does not throw") {
                    fluent.toBeFun(test)
                }
//                test("$notToBe throws AssertionError") {
//                    expect {
//                        fluent.notToBeFun(test)
//                    }.toThrow<AssertionError>{}
//                }
//                test("$isSame does not throw") {
//                    fluent.isSameFun(test)
//                }
//                test("$isNotSame throws AssertionError") {
//                    expect {
//                        fluent.isNotSameFun(test)
//                    }.toThrow<AssertionError>{}
//                }
            }
            context("not same but one equals the other") {
                val other = DataClass(true)
                it("$toBe does not throw") {
                    fluent.toBeFun(other)
                }
//                test("$notToBe throws AssertionError") {
//                    expect {
//                        fluent.notToBeFun(other)
//                    }.toThrow<AssertionError>{}
//                }
//                test("$isSame throws AssertionError") {
//                    expect {
//                        fluent.isSameFun(other)
//                    }.toThrow<AssertionError>{}
//                }
//                test("$isNotSame does not throw") {
//                    fluent.isNotSameFun(other)
//                }
            }
            context("one does not equal the other") {
                val other = DataClass(false)
                it("$toBe does not throw") {
                    expect {
                        fluent.toBeFun(other)
                    }.toThrow<AssertionError> {}
                }
//                test("$notToBe throws AssertionError") {
//                    fluent.notToBeFun(other)
//                }
//                test("$isSame throws AssertionError") {
//                    expect {
//                        fluent.isSameFun(other)
//                    }.toThrow<AssertionError>{}
//                }
//                test("$isNotSame does not throw") {
//                    fluent.isNotSameFun(other)
//                }
            }
        }
    }

//    describeFun(toBeNull) {
//
//        context("subject is null") {
//            val subject: Int? = null
//            it("does not throw an Exception") {
//                assertNullable(subject).toBeNullFun()
//            }
//        }
//
//        context("subject is not null") {
//            val subject: Int? = 1
//            val testee = assertNullable(1)
//            val expectFun = verbs.checkException {
//                testee.toBeNullFun()
//            }
//            setUp("throws an AssertionError") {
//                context("exception message") {
//                    it("contains the '${testee::subject.name}'") {
//                        expectFun.toThrow<AssertionError> { messageContains(subject.toString()) }
//                    }
//                    it("contains the '${DescriptiveAssertion::description.name}' of the assertion-message - which should be '${TO_BE.getDefault()}'") {
//                        expectFun.toThrow<AssertionError> { messageContains(TO_BE.getDefault()) }
//                    }
//                    it("contains the '${DescriptiveAssertion::representation.name}' of the assertion-message") {
//                        expectFun.toThrow<AssertionError> { messageContains(RawString.NULL.string) }
//                    }
//                }
//            }
//        }
//    }
//
//    describeFun(toBeNullable) {
//
//        context("subject is null") {
//            val subject: Int? = null
//            it("does not throw if null is passed") {
//                assertNullable(subject).toBeNullableFun(null)
//            }
//            it("throws an AssertionError if not null is passed") {
//                expect {
//                    assertNullable(subject).toBeNullableFun(1)
//                }.toThrow<AssertionError> {
//                    messageContains(": null", "${TO_BE.getDefault()}: 1")
//                }
//            }
//        }
//
//        context("subject is not null") {
//            val subject: Int? = 1
//            it("does not throw if expected is subject") {
//                assertNullable(subject).toBeNullableFun(subject)
//            }
//            it("throws an AssertionError if null is passed"){
//                expect{
//                    assertNullable(subject).toBeNullableFun(null)
//                }.toThrow<AssertionError> {
//                    messageContains(": 1", "${TO_BE.getDefault()}: null")
//                }
//            }
//            it("throws an AssertionError if expected does not equal subject"){
//                expect{
//                    assertNullable(subject).toBeNullableFun(2)
//                }.toThrow<AssertionError> {
//                    messageContains(": 1", "${TO_BE.getDefault()}: 2")
//                }
//            }
//        }
//    }
//
//    describeFun(toBeNullIfNullElse) {
//
//        context("subject is null") {
//            val subject: Int? = null
//            it("does not throw if null is passed") {
//                assertNullable(subject).toBeNullIfNullElseFun(null)
//            }
//            it("throws an AssertionError if not null is passed") {
//                expect {
//                    assertNullable(subject).toBeNullIfNullElseFun { toBe(1) }
//                }.toThrow<AssertionError> {
//                    messageContains(": null", "${TO_BE.getDefault()}: 1")
//                }
//            }
//        }
//
//        context("subject is not null") {
//            val subject: Int? = 1
////            it("does not throw if sub assertion holds") {
////                assertNullable(subject).toBeNullIfNullElseFun { isLessThan(2) }
////            }
////            it("throws an AssertionError if sub assertion does not hold"){
////                expect{
////                    assertNullable(subject).toBeNullIfNullElseFun{ isGreaterThan(1) }
////                }.toThrow<AssertionError> {
////                    messageContains(": 1", "${DescriptionComparableAssertion.IS_GREATER_THAN.getDefault()}: 1")
////                }
////            }
//            it("throws an AssertionError if null is passed"){
//                expect{
//                    assertNullable(subject).toBeNullIfNullElseFun(null)
//                }.toThrow<AssertionError> {
//                    messageContains(": 1", "${TO_BE.getDefault()}: null")
//                }
//            }
//
//        }
//    }

//
//    prefixedDescribe("property `$and` immediate") {
//        it("returns the same plant") {
//            val plant = assert(1)
//            verbs.checkImmediately(plant.andProperty()).toBe(plant)
//        }
//    }
//    prefixedDescribe("`$andLazy` group") {
//        it("returns the same plant") {
//            val plant = assert(1)
//            verbs.checkImmediately(plant.andLazyGroup { }).toBe(plant)
//        }
//    }

}) {
    interface AnyAssertionsSpecFunFactory<T> {
        val toBeFun: Expect<T>.(T) -> Expect<T>
//        val notToBeFun: Expect<T>.(T) -> Expect<T>
//        val isSameFun: Expect<T>.(T) -> Expect<T>
//        val isNotSameFun: Expect<T>.(T) -> Expect<T>
    }

    data class DataClass(val isWhatever: Boolean)
}
